package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;

import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PostsController {

    @Autowired
    PostRepository repository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")

    public String index(Model model, Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        // code above to get email from the authenticator
        Optional<User> user = userRepository.findUserByUsername(username);
        // ^^ optional user, theoretical
        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }
        // ^^ if the user is not saved in our database, they get redirected to the registration page

        Long userId = user.get().getId(); // getting id from database - checking that id is connected
        String email = user.get().getUsername(); // getting email in same way
  
  
  
//     public String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
  //         String userName = principal.getAttribute("email");
//         Optional<User> user = userRepository.findUserByUsername(userName);
//         long id = user.map(User::getId).orElse(999999999999L);
//         model.addAttribute("userID",id);


        Iterable<Post> posts = repository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());

        // code below to get userId and email from database
        model.addAttribute("userId", userId);
        model.addAttribute("email", email);

        return "posts/index";
    }

    @PostMapping("/posts")
    public RedirectView create(@ModelAttribute Post post) {
        System.out.println(post.getId());
        repository.save(post);
        return new RedirectView("/posts");
    }
}
