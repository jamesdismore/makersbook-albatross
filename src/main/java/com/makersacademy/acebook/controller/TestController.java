package com.makersacademy.acebook.controller;


import org.springframework.ui.Model;
import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.PendingFriendRequestForm;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @GetMapping("/test")
    public String testpage(Model model) {
        ArrayList<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestByfromUserIdAndStatus(7,"PENDING");
        List<PendingFriendRequestForm> pendingRequests = populatePendingRequestsFormUserRequestRow(friendRequests);
        model.addAttribute("pendingRequests", pendingRequests);
        return "friendRequests";
    }

    private List<PendingFriendRequestForm> populatePendingRequestsFormUserRequestRow(ArrayList<FriendRequest> friendRequests) {
        List<PendingFriendRequestForm> pendingRequests = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            PendingFriendRequestForm pendingFriendRequestForm = new PendingFriendRequestForm();

            long recipientId = friendRequest.getToUserId();
            long senderId = friendRequest.getFromUserId();

            User recipient = userRepository.findById(recipientId).orElseThrow();
            User sender = userRepository.findById(senderId).orElseThrow();

            pendingFriendRequestForm.setRecipientId(recipientId);
            pendingFriendRequestForm.setFirstName(recipient.getFirstName());
            pendingFriendRequestForm.setLastName(recipient.getLastName());
            pendingFriendRequestForm.setTimestamp(friendRequest.getRequestTimestamp().toLocalDateTime());
            pendingFriendRequestForm.setRequestMessage("XYZ");
            pendingRequests.add(pendingFriendRequestForm);

        }

        return pendingRequests;

    }
}
