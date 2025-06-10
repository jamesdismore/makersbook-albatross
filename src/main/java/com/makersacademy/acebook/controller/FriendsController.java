package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class FriendsController {
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserRepository userRepository;

    @ModelAttribute("user")
    public Optional<User> getUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        return userRepository.findUserByUsername(username);
    }

    @GetMapping("/friends")
    public String friends(Model model, Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }
        Long userIdLong = user.get().getId(); // getting id from database - checking that id is connected
        int userId = Math.toIntExact(userIdLong);
        ArrayList<Friendship> friendsListArray =  friendshipRepository.findFriendshipByUserId(userId);

//        String friendsListEmails = "";
        ArrayList<User> friendsUserListArray = new ArrayList<User>();


//        int arrayPos = 0;
        for (Friendship friendship :friendsListArray){
            Optional<User> friendOptional = userRepository.findById((long) friendship.getFriendId());
            User friend = friendOptional.get();
            friendsUserListArray.add(friend);
        }
        model.addAttribute("friends",friendsUserListArray);
        model.addAttribute("user",user.get());
        return "friends";
    }

    @PostMapping("/friends/unfriend")
    public String unfriend(@RequestParam("unfriendId") Long unfriendId,@RequestParam("userId") Long userId) {
        int unfriendInt = unfriendId.intValue();
        int userInt = userId.intValue();
        ArrayList<Friendship> friendshipList = friendshipRepository.findFriendshipByUserIdAndFriendId(unfriendInt,userInt);
        ArrayList<Friendship> friendshipFrom = friendshipRepository.findFriendshipByUserIdAndFriendId(userInt,unfriendInt);
        friendshipList.addAll(friendshipFrom);


        for (Friendship friendship : friendshipList){
            friendshipRepository.delete(friendship);
        }
        return "redirect:/posts";
    }

}
