package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.*;

import com.makersacademy.acebook.repository.*;
import jakarta.transaction.Transactional;
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

    @GetMapping("/posts")
    public String index(@ModelAttribute("user") Optional<User> user, Model model) {


        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }

        Iterable<Post> posts = postRepository.findAllByOrderByIdDesc();
        Iterable<Comment> comments = commentRepository.findAll();

        Post newPost = new Post();
        Comment newComment = new Comment();

        Long userId = user.get().getId();

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

        // Map to store like status for each post
        Map<Long, Boolean> likedPosts = new HashMap<>();
        Map<Long, Integer> likeCountsPosts = new HashMap<>();
        for (Post post : posts) {
            likedPosts.put(post.getId(), postLikeRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
            likeCountsPosts.put(post.getId(), postLikeRepository.countByPostId(post.getId())); // Fetch like count
        }
        model.addAttribute("likedPosts", likedPosts);
        model.addAttribute("likeCountsPosts", likeCountsPosts);

        // Map to store like status for each comment
        Map<Long, Boolean> likedComments = new HashMap<>();
        Map<Long, Integer> likeCountsComments = new HashMap<>();
        for (Comment comment : comments) {
            likedComments.put(comment.getId(), commentLikeRepository.findByUserIdAndCommentId(userId, comment.getId()).isPresent());
            likeCountsComments.put(comment.getId(), commentLikeRepository.countByCommentId(comment.getId())); // Fetch like count
        }
        model.addAttribute("likedComments", likedComments);
        model.addAttribute("likeCountsComments", likeCountsComments);

        return "posts";
    }

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

    @PostMapping("/posts/post/{postId}/like")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable Long postId, @ModelAttribute("user") Optional<User> user) {
        Map<String, Object> response = new HashMap<>();

        Long userId = user.get().getId();

        Optional<PostLike> existingLike = postLikeRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            // Unlike post
            postLikeRepository.delete(existingLike.get());
            response.put("liked", false);
        } else {
            // Like post
            PostLike newLike = new PostLike(userId, postId);
            postLikeRepository.save(newLike);
            response.put("liked", true);
        }
        // Like count updated
        response.put("likes", postLikeRepository.countByPostId(postId));

        return response;
    }

    @PostMapping("/posts/comment/{commentId}/like")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable long commentId, @ModelAttribute("user") Optional<User> user) {
        Map<String, Object> response = new HashMap<>();

        Long userId = user.get().getId();

        Optional<CommentLike> existingLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId);

        if (existingLike.isPresent()) {
            // Unlike comment
            commentLikeRepository.delete(existingLike.get());
            response.put("liked", false);
        } else {
            // Like comment
            CommentLike newLike = new CommentLike(userId, commentId);
            commentLikeRepository.save(newLike);
            response.put("liked", true);
        }
        // Like count updated
        response.put("likes", commentLikeRepository.countByCommentId(commentId));

        return response;
    }

    @PostMapping("/posts/comment/{commentId}/delete")
    @Transactional
    public RedirectView delete(@PathVariable long commentId, Model model){
        commentLikeRepository.deleteByCommentId(commentId);
        commentRepository.deleteById(commentId);
        return new RedirectView("/posts");
    }
}
