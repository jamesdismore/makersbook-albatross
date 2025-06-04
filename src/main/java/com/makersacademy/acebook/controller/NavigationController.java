// STOP PEOPLE BEING SNEAKY IF NOT REGISTERED
/*
    import com.makersacademy.acebook.repository.UserRepository;
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

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
public class NavigationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        // code above to get email from the authenticator
        Optional<User> user = userRepository.findUserByUsername(username);
        // ^^ optional user, theoretical
        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }
        // ^^ if the user is not saved in our database, they get redirected to the registration page
        return "profile";
    }

    @GetMapping("/settings")
    public String settings(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        // code above to get email from the authenticator
        Optional<User> user = userRepository.findUserByUsername(username);
        // ^^ optional user, theoretical
        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }
        // ^^ if the user is not saved in our database, they get redirected to the registration page

        return "settings";
    }

}
