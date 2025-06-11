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

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.util.AvatarAssistant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Controller
public class NavigationController {

    @Autowired
    UserRepository userRepository;

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
        } else {
            return "profile";
        }
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


}
