package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;

import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @ModelAttribute("user")
    public Optional<User> getUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        return userRepository.findUserByUsername(username);
    }

    @GetMapping("/posts")
    public String index(@ModelAttribute("user") Optional<User> user, Model model) {


        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }

        Iterable<Post> posts = postRepository.findAllByOrderByIdDesc();
        Iterable<Comment> comments = commentRepository.findAll();

        Post newPost = new Post();
        Comment newComment = new Comment();

        model.addAttribute("posts", posts);
        model.addAttribute("post", newPost);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", newComment);

        // code below to get userId and email from database
        model.addAttribute("userId", user.get().getId());
        model.addAttribute("email", user.get().getUsername());

        // Create a map to store post authors
        Map<Long, User> postAuthors = new HashMap<>();
        for (Post post : posts) {
            Optional<User> postUser = userRepository.findById(post.getUser_id());
            postUser.ifPresent(user1 -> postAuthors.put(post.getId(), user1));
        }
        model.addAttribute("postAuthors", postAuthors);

        // Create a map to store comment authors
        Map<Long, User> commentAuthors = new HashMap<>();
        for (Comment comment : comments) {
            Optional<User> commentUser = userRepository.findById(comment.getUserId());
            commentUser.ifPresent(user1 -> commentAuthors.put(comment.getId(), user1));
        }
        model.addAttribute("commentAuthors", commentAuthors);

        return "index";
    }

//    @GetMapping("/posts/comment")
//    public String indexComment(@ModelAttribute("user") Optional<User> user,  Model model) {
//
//        Iterable<Post> posts = postRepository.findAll();
//        Post newPost = new Post();
//        model.addAttribute("posts", posts);
//        model.addAttribute("post", newPost);
//
//        Iterable<Comment> comments = commentRepository.findAll();
//        Comment newComment = new Comment();
//        model.addAttribute("comments", comments);
//        model.addAttribute("comment", newComment);
//
//        model.addAttribute("userId", user.get().getId());
//        model.addAttribute("email", user.get().getUsername());
//
//
//        return "index";
//    }

    @PostMapping("/posts")
    public RedirectView createPost(@ModelAttribute Post post) {
        postRepository.save(post);
        return new RedirectView("/posts");
    }

    @PostMapping("/posts/comment")
    public RedirectView createComment(@ModelAttribute Comment comment, Model model) {
        commentRepository.save(comment);
        return new RedirectView("/posts");
    }
}
