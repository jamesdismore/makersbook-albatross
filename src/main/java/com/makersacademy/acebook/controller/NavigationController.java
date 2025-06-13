// STOP PEOPLE BEING SNEAKY IF NOT REGISTERED
/*
    import com.makersacademy.acebook.postRepository.UserRepository;
    import org.springframework.security.core.Authentication; <- replaces the tomcat one
    @Autowired
    UserRepository userRepository;

    public String exampleMethod(Authentication authentication)
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        Optional<User> user = userRepository.findUserByUsername(username);
                if (user.isEmpty()) {
                return "redirect:/users/newUser";
        }
        return "whateverhtmlyouwant"
*/

package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.util.AvatarAssistant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Controller
public class NavigationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @ModelAttribute("user")
    public Optional<User> getUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        return userRepository.findUserByUsername(username);
    }

    // Routes ------

    @GetMapping("/profile")
    public String profile(@ModelAttribute("user") Optional<User> user, Model model) {
        if (user.isEmpty()) {
            return "redirect:/users/newUser";
        }
            return "redirect:/profile/" + user.get().getId();
    }

    @GetMapping("/settings")
    public String settings(@ModelAttribute("user") Optional<User> user) {

        return user.isEmpty() ? "redirect:users/newUser" : "settings";
   }

    @PostMapping("/settings")
    public String settings(@Valid User user, BindingResult bindingResult, @RequestParam("file") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "settings";
        } else {
            if (!imageFile.isEmpty()) {
                AvatarAssistant.overwriteAvatar(imageFile, user);
            }
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("updatesSavedMessage", "Your details have been updated");
            return "redirect:/settings";
        }
    }

    @GetMapping("/deleteconfirmation")
    public String deleteConfirmation(@ModelAttribute("user") Optional<User> user,Model model) {
        model.addAttribute("deleteconfirmation", true);

        return "settings";
    }

    @PostMapping("/deleteconfirmation")
    public String deleteUser(@ModelAttribute("userId")long userId){
        User deletedUser = new User();
        deletedUser.setId(userId);
        deletedUser.setAvatar("deleteduser");
        String newUsername = getAlphaNumericString(12);
        deletedUser.setUsername(newUsername);
        deletedUser.setFirstName("Deleted");
        deletedUser.setLastName("User");
        deletedUser.setDob(LocalDate.parse("1900-01-01"));

        ArrayList<Friendship> friendshipsToDelete = friendshipRepository.findFriendshipByUserIdOrFriendId(Math.toIntExact(userId),Math.toIntExact(userId));
        ArrayList<FriendRequest> friendRequestsToDelete = friendRequestRepository.findFriendRequestByFromUserIdOrToUserId(userId,userId);

        for (Friendship friendship : friendshipsToDelete){
            friendshipRepository.deleteById(friendship.getId());
        }

        for (FriendRequest friendRequest : friendRequestsToDelete){
            friendRequestRepository.deleteById(friendRequest.getId());
        }


        userRepository.save(deletedUser);
        return "redirect:/logout";
    }




    static String getAlphaNumericString(int n)
        {

            // length is bounded by 256 Character
            byte[] array = new byte[256];
            new Random().nextBytes(array);

            String randomString
                    = new String(array, StandardCharsets.UTF_8);

            // Create a StringBuffer to store the result
            StringBuffer r = new StringBuffer();

            // Append first 20 alphanumeric characters
            // from the generated random String into the result
            for (int k = 0; k < randomString.length(); k++) {

                char ch = randomString.charAt(k);

                if (((ch >= 'a' && ch <= 'z')
                        || (ch >= 'A' && ch <= 'Z')
                        || (ch >= '0' && ch <= '9'))
                        && (n > 0)) {

                    r.append(ch);
                    n--;
                }
            }

            // return the resultant string
            return r.toString();
        }

}
