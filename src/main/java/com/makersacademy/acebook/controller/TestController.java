package com.makersacademy.acebook.controller;


import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {
    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    UserRepository userRepository;

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

    @GetMapping("/testjsql")
    public String testjsql(){
        ArrayList<User> output = userRepository.approximateUserSearch("sa");
        return output.toString();
    }

    @GetMapping("/testusersearch")
    public String testusersearch(){
        String query = "mA";
        String queryLow = query.toLowerCase();
        ArrayList<User> approxOutput = userRepository.approximateUserSearch(queryLow);
        ArrayList<User> exactOutput = userRepository.exactUserSearch(queryLow);
        ArrayList<User> sortedOutput = exactOutput;
        for (User user : approxOutput)
        {
            if(!exactOutput.contains(user)){
                sortedOutput.add(user);
            }
        }

        return sortedOutput.toString();
    }
}

