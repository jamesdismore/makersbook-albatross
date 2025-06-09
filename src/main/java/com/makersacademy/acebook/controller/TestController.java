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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PostExchange;

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

    @PostMapping("/friends/closeRequest")
    String closeRequest(@RequestParam("id") Long requestId) {
        friendRequestRepository.deleteById(requestId);
        return "redirect:/test";
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
            pendingFriendRequestForm.setRequestMessage(friendRequest.getRequestMessage());
            pendingFriendRequestForm.setRequestId(friendRequest.getId());
            pendingRequests.add(pendingFriendRequestForm);

        }
        return pendingRequests;

    }




    @GetMapping("/testacceptorreject")
    public String testpagependingto(){
        ArrayList<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestBytoUserIdAndStatus(7,"PENDING");
        return friendRequests.toString();
    }
}
