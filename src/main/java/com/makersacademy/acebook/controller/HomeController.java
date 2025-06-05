package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepository;

	@ModelAttribute("user")
	Optional<User> findUser(Authentication authentication) {
		DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
		String username = (String) principal.getAttributes().get("email");
		return userRepository.findUserByUsername(username);
	}

	// Routes ------

	@GetMapping(value = "/")
	public String index(@ModelAttribute("user") Optional<User> user) {
        return user.
				map(_user -> "redirect:/users/" + _user.getUsername())
				.orElse("redirect:/users/newUser");
	}
}
