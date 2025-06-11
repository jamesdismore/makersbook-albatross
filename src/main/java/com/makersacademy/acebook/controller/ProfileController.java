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
        Iterable<Post> userPosts = postRepository.findByUserIdOrderByIdDesc(userId);
        Iterable<Comment> comments = commentRepository.findAll();

        // Create a map to store post authors
        Map<Long, User> postAuthors = new HashMap<>();
        for (Post post : userPosts) {
            Optional<User> postUser = userRepository.findById(post.getUser_id());
            postUser.ifPresent(user1 -> postAuthors.put(post.getId(), user1));
        }

        // Create a map to store comment authors
        Map<Long, User> commentAuthors = new HashMap<>();
        for (Comment comment : comments) {
            Optional<User> commentUser = userRepository.findById(comment.getUserId());
            commentUser.ifPresent(user1 -> commentAuthors.put(comment.getId(), user1));
        }

        // Map to store like status for each post
        Map<Long, Boolean> likedPosts = new HashMap<>();
        Map<Long, Integer> likeCountsPosts = new HashMap<>();
        for (Post post : userPosts) {
            likedPosts.put(post.getId(), postLikeRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
            likeCountsPosts.put(post.getId(), postLikeRepository.countByPostId(post.getId())); // Fetch like count
        }

        // Map to store like status for each comment
        Map<Long, Boolean> likedComments = new HashMap<>();
        Map<Long, Integer> likeCountsComments = new HashMap<>();
        for (Comment comment : comments) {
            likedComments.put(comment.getId(), commentLikeRepository.findByUserIdAndCommentId(userId, comment.getId()).isPresent());
            likeCountsComments.put(comment.getId(), commentLikeRepository.countByCommentId(comment.getId())); // Fetch like count
        }

        model.addAttribute("profileUser", profileUser.get());
        model.addAttribute("posts", userPosts);
        model.addAttribute("comments", comments);
        model.addAttribute("postAuthors", postAuthors);
        model.addAttribute("commentAuthors", commentAuthors);
        model.addAttribute("likedPosts", likedPosts);
        model.addAttribute("likeCountsPosts", likeCountsPosts);
        model.addAttribute("likedComments", likedComments);
        model.addAttribute("likeCountsComments", likeCountsComments);

        return "profile";
    }

}
