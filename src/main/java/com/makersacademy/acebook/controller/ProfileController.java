package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @ModelAttribute("user")
    public Optional<User> getUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        return userRepository.findUserByUsername(username);
    }

    @GetMapping("/profile/{userId}")
    public String getProfile(@PathVariable Long userId, @ModelAttribute("user") Optional<User> loggedInUser, Model model) {


        if (loggedInUser.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }

        Optional<User> profileUser = userRepository.findById(userId);
        model.addAttribute("profileUser", profileUser.get());

        return "profile";
    }

}
