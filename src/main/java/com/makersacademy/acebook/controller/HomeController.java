package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class HomeController {
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/")
	public RedirectView index(Authentication authentication) {
		DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
		String username = (String) principal.getAttributes().get("email");
		// code above to get email from the authenticator
		Optional<User> user = userRepository.findUserByUsername(username);
		// ^^ optional user, theoretical
		System.out.println("handing /");
		if (user.isEmpty()) {
			return new RedirectView("/users/newUser");// Redirect if not registered
		}
		// ^^ if the user is not saved in our database, they get redirected to the registration page

		return new RedirectView("/posts");
	}
}
