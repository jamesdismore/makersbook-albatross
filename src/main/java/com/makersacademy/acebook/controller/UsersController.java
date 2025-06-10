package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping
public class UsersController {
    @Autowired
    UserRepository userRepository;


    @ModelAttribute("user")
    public User getUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");

        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent()) {
            return user.get();
        } else {
            return new User(username);
        }
    }

    @GetMapping("/users/after-login")
    public RedirectView afterLogin(@ModelAttribute("user") User user) {
        System.out.println("Start of afterLogin:" + user);
        if (user.getFirstName() == null){
            System.out.println("Brand new user needs to register");
            return new RedirectView("/users/newUser"); // redirect to registration if missing
        } else {
            System.out.println("User exists");
            return new RedirectView("/posts"); // redirect to posts if registered
        }

    }



    @GetMapping("/users/newUser")
    public String afterSignUp(@ModelAttribute("user") User user) {
        return "newUser";

    }

    @PostMapping("/users/newUser")
    public String saveNewUser(@Valid @ModelAttribute("user") User user, @RequestParam("file") MultipartFile file, BindingResult result) {
        if (result.hasErrors()) { // this currently does nilch
            return "newUser"; //stays on page
        }
        userRepository.save(user);

        if (!file.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/static/images/userAvatars/";
                String filename = String.valueOf(user.getId()); // save as {userId}.jpg
                Path path = Paths.get(uploadDir + filename);
                Files.write(path, file.getBytes());

                // Set filename in the user object & update database
                user.setAvatar(filename); // Store "9.jpg" in DB
                userRepository.save(user);  // Save user with updated avatar path
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/posts"; // redirects to posts
    }

//    @GetMapping("/users/after-login")
//    public RedirectView afterLogin() {
//        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        String username = (String) principal.getAttributes().get("email");
//        Optional<User> user = userRepository.findUserByUsername(username);
//        if (user.isPresent()){
//            return new RedirectView("/posts"); // redirect to posts if registered
//        } else {
//            return new RedirectView("/users/newUser"); // redirect to registration if missing
//        }
//
//    }
//    @GetMapping("/users/newUser")
//    public String afterSignUp(@ModelAttribute("our_user") User user) {
//        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        String username = (String) principal.getAttributes().get("email");
//        user.setUsername(username); // automatically fills in username on new user field, perhaps make this not allowed to change? once we add FN and LN
//        return "newUser";
//
//    }
//    @PostMapping("/users/newUser")
//    public String saveNewUser(@Valid @ModelAttribute("our_user") User user, BindingResult result) {
//        if (result.hasErrors()) { // error messages come from class constraints (needs dependency)
//            return "newUser"; //stays on page
//        } else {
//            userRepository.save(user); // this saves user to database, eventually fill out parameters as database changes to include first name, last name, dob etc
//            return "redirect:/posts"; // redirects to posts
//        }
//    }
}
