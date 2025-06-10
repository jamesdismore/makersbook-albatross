package com.makersacademy.acebook.controller;


import com.makersacademy.acebook.model.forms.FriendRequestForm;
import com.makersacademy.acebook.model.forms.FriendRequestResponseForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.Model;
import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.forms.PendingFriendRequestForm;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ManageFriendsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @ModelAttribute("user")
    Optional<User> findUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        return userRepository.findUserByUsername(username);
    }

    @GetMapping("/manageFriends")
    public String manageFriends(
            @RequestParam(value = "recipientId", required = false) Optional<Long> recipientId,
            Model model,
            @ModelAttribute("user") Optional<User> oUser) {

        User user = oUser.orElseThrow();
        // New request form
        if (recipientId.isPresent()) {
            FriendRequestForm friendRequestForm = prepareNewRequestForm(
                    recipientId.orElseThrow(),
                    user);
            model.addAttribute("friendRequestForm", friendRequestForm);
        }

        // Gather pending requests
        List<PendingFriendRequestForm> pendingRequestForms = pendingRequestsFor(user);
        model.addAttribute("pendingRequests", pendingRequestForms);

        // Gather accepted requests
        List<FriendRequestResponseForm> acceptedRequestForms = acceptedRequestsFor(user);
        model.addAttribute("acceptedRequests", acceptedRequestForms);

        // Gather rejected requests
        List<FriendRequestResponseForm> rejectedRequestForms = rejectedRequestsFor(user);
        model.addAttribute("rejectedRequests", rejectedRequestForms);

        return "friendRequests";
    }


    // Handling button clicks within the templates.

    @PostMapping("/manageFriends/submitFriendRequest")
    public String submitFriendRequest(@ModelAttribute("friendRequestForm") FriendRequestForm requestForm) {
        FriendRequest friendRequest = FriendRequest.fromForm(requestForm);
        friendRequestRepository.save(friendRequest);
        return "redirect:/manageFriends";
    }

    @PostMapping("/manageFriends/closeRequest")
    String closeRequest(@RequestParam("id") Long requestId) {
        friendRequestRepository.deleteById(requestId);
        return "redirect:/manageFriends";
    }


    // Post request convenience handlers

    // Gathering information to pass in to the template

    // Set up the new-request bean
    private FriendRequestForm prepareNewRequestForm(long targetId, User user) {
        User recipient = userRepository.findById(targetId).orElseThrow();
        return new FriendRequestForm(user, recipient);
    }


    // Gather pending requests

    private List<PendingFriendRequestForm> pendingRequestsFor(User user) {
        ArrayList<FriendRequest> pendingRequests = friendRequestRepository.findFriendRequestByfromUserIdAndStatus(user.getId(), "PENDING");
        return buildPendingForms(pendingRequests);
    }

    private List<PendingFriendRequestForm> buildPendingForms(ArrayList<FriendRequest> friendRequests) {
        List<PendingFriendRequestForm> pendingRequests = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            PendingFriendRequestForm form = new PendingFriendRequestForm();

            long recipientId = friendRequest.getToUserId();
            User recipient = userRepository.findById(recipientId).orElseThrow();

            form.setRecipientId(recipientId);
            form.setFirstName(recipient.getFirstName());
            form.setLastName(recipient.getLastName());
            form.setTimestamp(friendRequest.getRequestTimestamp().toLocalDateTime());
            form.setRequestMessage(friendRequest.getRequestMessage());
            form.setRequestId(friendRequest.getId());
            pendingRequests.add(form);

        }
        return pendingRequests;

    }

    // Gather accepted requests
    private List<FriendRequestResponseForm> acceptedRequestsFor(User user) {
        ArrayList<FriendRequest> acceptedRequests = friendRequestRepository.findFriendRequestByfromUserIdAndStatus(user.getId(), "ACCEPTED");
        return buildAcceptedForms(acceptedRequests);
    }


    private List<FriendRequestResponseForm> buildAcceptedForms(ArrayList<FriendRequest> acceptedRequests) {
        List<FriendRequestResponseForm> forms = new ArrayList<>();

        for (FriendRequest friendRequest : acceptedRequests) {
            FriendRequestResponseForm form = new FriendRequestResponseForm();
            form.setStatus("ACCEPTED");
            form.setRequestId(friendRequest.getId());

            long recipientId = friendRequest.getToUserId();
            User recipient = userRepository.findById(recipientId).orElseThrow();

            form.setRecipientId(recipientId);
            form.setSenderId(friendRequest.getFromUserId());

            form.setFirstName(recipient.getFirstName());
            form.setLastName(recipient.getLastName());

            form.setTimestamp(friendRequest.getResponseTimestamp().toLocalDateTime());
            form.setMessage(friendRequest.getResponseMessage());

            forms.add(form);
        }

        return forms;
    }


    // Gather rejected requests
    private List<FriendRequestResponseForm> rejectedRequestsFor(User user) {
        ArrayList<FriendRequest> rejectedRequests = friendRequestRepository.findFriendRequestByfromUserIdAndStatus(user.getId(), "REJECTED");
        return buildAcceptedForms(rejectedRequests);
    }

    private List<FriendRequestResponseForm> buildRejectedForms(ArrayList<FriendRequest> rejectedRequests) {
        List<FriendRequestResponseForm> forms = new ArrayList<>();

        for (FriendRequest friendRequest : rejectedRequests) {
            FriendRequestResponseForm form = new FriendRequestResponseForm();
            form.setStatus("REJECTED");
            form.setRequestId(friendRequest.getId());

            long recipientId = friendRequest.getToUserId();
            User recipient = userRepository.findById(recipientId).orElseThrow();

            form.setRecipientId(recipientId);
            form.setSenderId(friendRequest.getFromUserId());

            form.setFirstName(recipient.getFirstName());
            form.setLastName(recipient.getLastName());

            form.setTimestamp(friendRequest.getResponseTimestamp().toLocalDateTime());
            form.setMessage(friendRequest.getResponseMessage());

            forms.add(form);
        }

        return forms;
    }

}