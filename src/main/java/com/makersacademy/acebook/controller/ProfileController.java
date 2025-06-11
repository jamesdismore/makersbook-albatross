package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        Comment newComment = new Comment();

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
        model.addAttribute("comment", newComment);
        model.addAttribute("postAuthors", postAuthors);
        model.addAttribute("commentAuthors", commentAuthors);
        model.addAttribute("likedPosts", likedPosts);
        model.addAttribute("likeCountsPosts", likeCountsPosts);
        model.addAttribute("likedComments", likedComments);
        model.addAttribute("likeCountsComments", likeCountsComments);

        return "profile";
    }

    @PostMapping("/profile/{userId}/comment")
    public String createComment(@PathVariable Long userId, @ModelAttribute Comment comment, Model model) {
        commentRepository.save(comment);
        return "redirect:/profile/" + userId;
    }

    @PostMapping("/profile/post/{postId}/like")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable Long postId, @ModelAttribute("user") Optional<User> loggedInUser) {
        Map<String, Object> response = new HashMap<>();

        Long loggedInUserId = loggedInUser.get().getId();

        Optional<PostLike> existingLike = postLikeRepository.findByUserIdAndPostId(loggedInUserId, postId);

        if (existingLike.isPresent()) {
            // Unlike post
            postLikeRepository.delete(existingLike.get());
            response.put("liked", false);
        } else {
            // Like post
            PostLike newLike = new PostLike(loggedInUserId, postId);
            postLikeRepository.save(newLike);
            response.put("liked", true);
        }
        // Like count updated
        response.put("likes", postLikeRepository.countByPostId(postId));

        return response;
    }

    @PostMapping("/profile/comment/{commentId}/like")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable long commentId, @ModelAttribute("user") Optional<User> loggedInUser) {
        Map<String, Object> response = new HashMap<>();

        Long loggedInUserId = loggedInUser.get().getId();

        Optional<CommentLike> existingLike = commentLikeRepository.findByUserIdAndCommentId(loggedInUserId, commentId);

        if (existingLike.isPresent()) {
            // Unlike comment
            commentLikeRepository.delete(existingLike.get());
            response.put("liked", false);
        } else {
            // Like comment
            CommentLike newLike = new CommentLike(loggedInUserId, commentId);
            commentLikeRepository.save(newLike);
            response.put("liked", true);
        }
        // Like count updated
        response.put("likes", commentLikeRepository.countByCommentId(commentId));

        return response;
    }

}
