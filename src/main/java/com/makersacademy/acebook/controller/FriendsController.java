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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class FriendsController {
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/friends")
    public String friends(Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }
        Long userIdLong = user.get().getId(); // getting id from database - checking that id is connected
        int userId = Math.toIntExact(userIdLong);
        ArrayList<Friendship> friendsListTotal = new ArrayList<Friendship>();
        ArrayList<Friendship> friendsListLower =  friendshipRepository.findFriendshipByLowerUserId(userId);
        ArrayList<Friendship> friendsListHigher =  friendshipRepository.findFriendshipByHigherUserId(userId);
        ArrayList<Friendship> friendsListLowerOrHigher = friendshipRepository.findFriendshipByLowerUserIdOrHigherUserId(userId, userId);
        String friendslist = "";
        for (Friendship friendship :friendsListHigher){
            Optional<User> otherFriendOptional = userRepository.findById((long) friendship.getLowerUserId());
            User otherFriend = otherFriendOptional.get();
            friendslist = friendslist + " " + otherFriend.getUsername();
        }
        return friendsListLowerOrHigher.toString();
    }

}
