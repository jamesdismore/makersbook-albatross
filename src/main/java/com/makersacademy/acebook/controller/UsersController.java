package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/after-login")
    public RedirectView afterLogin() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = (String) principal.getAttributes().get("email");
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()){
            System.out.println(user.get().getId());
            return new RedirectView("/posts");
        } else {
            return new RedirectView("/users/newUser");
        }
        // below -> previous method of returning posts w email welcome OR saving user and then logging in
            //
//        userRepository
//                .findUserByUsername(username)
//                .orElseGet(() -> userRepository.save(new User(username)));
//
//        return new RedirectView("/posts");
    }
    @GetMapping("/users/newUser")
    public ModelAndView afterSignUp() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // below - post mapping?
        String username = (String) principal.getAttributes().get("email");
        User user = new User(username);
        ModelAndView modelAndView = new ModelAndView("NewUserPage", "our_user", user);
        return modelAndView;
    }
    @PostMapping("/users/newUser")
    public RedirectView saveNewUser(@Valid User user, BindingResult result) {
        System.out.println("Check");
        if (result.hasErrors()) { // errors come from class constraints
            return new RedirectView("/users/newUser");
        } else {
            userRepository.save(user); // fill out parameters as database changes to include first name, last name, dob etc
            return new RedirectView("/posts");
        }
    }
}
