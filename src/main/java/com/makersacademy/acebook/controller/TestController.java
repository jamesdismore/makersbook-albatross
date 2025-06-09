package com.makersacademy.acebook.controller;


import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {
    @Autowired
    FriendRequestRepository friendRequestRepository;

    @GetMapping("/test")
    public String testpage(){
        ArrayList<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestByfromUserIdAndStatus(7,"PENDING");
        return friendRequests.toString();
    }

    @GetMapping("/testacceptorreject")
    public String testpagependingto(){
        ArrayList<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestBytoUserIdAndStatus(7,"PENDING");
        return friendRequests.toString();
    }
}
