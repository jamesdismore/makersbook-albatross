package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Controller
public class FriendsController {
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserRepository userRepository;

    @ModelAttribute("user")
    public Optional<User> getUser(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        return userRepository.findUserByUsername(username);
    }

    @GetMapping("/friends")
    public String friends(Model model, Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }
        Long userIdLong = user.get().getId(); // getting id from database - checking that id is connected
        int userId = Math.toIntExact(userIdLong);
        ArrayList<Friendship> friendsListArray =  friendshipRepository.findFriendshipByUserId(userId);

//        String friendsListEmails = "";
        ArrayList<User> friendsUserListArray = new ArrayList<User>();


//        int arrayPos = 0;
        for (Friendship friendship :friendsListArray){
            Optional<User> friendOptional = userRepository.findById((long) friendship.getFriendId());
            User friend = friendOptional.get();
            friendsUserListArray.add(friend);
        }
        model.addAttribute("friends",friendsUserListArray);
        model.addAttribute("user",user.get());
        return "friends";
    }

    @PostMapping("/friends/unfriend")
    public String unfriend(@RequestParam("unfriendId") Long unfriendId,@RequestParam("userId") Long userId) {
        int unfriendInt = unfriendId.intValue();
        int userInt = userId.intValue();
        ArrayList<Friendship> friendshipList = friendshipRepository.findFriendshipByUserIdAndFriendId(unfriendInt,userInt);
        ArrayList<Friendship> friendshipFrom = friendshipRepository.findFriendshipByUserIdAndFriendId(userInt,unfriendInt);
        friendshipList.addAll(friendshipFrom);


        for (Friendship friendship : friendshipList){
            friendshipRepository.delete(friendship);
        }
        return "redirect:/friends";
    }

    @PostMapping("/friends/search")
    public String searchFriends(@RequestParam(value = "searchQuery", defaultValue = "") String searchQuery, Model model, Authentication authentication) {
        // Get the logged-in user's details
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isEmpty()) {
            return "redirect:/users/newUser"; // Redirect if not registered
        }


        // Search for users matching the query
        String queryLow = searchQuery.toLowerCase();
        ArrayList<User> approxOutput = userRepository.approximateUserSearch(queryLow);
        ArrayList<User> exactOutput = userRepository.exactUserSearch(queryLow);
        for (User userSearched : approxOutput)
        {
            if(!exactOutput.contains(userSearched)){
                exactOutput.add(userSearched);
            }
        }
        ArrayList<Friendship> friendshipsArray = friendshipRepository.findFriendshipByUserId(user.get().getId().intValue());
        ArrayList<Long> friendIds = new ArrayList<>();
        for(Friendship friendship : friendshipsArray){
            friendIds.add(friendship.getFriendId());
        }
        exactOutput.removeIf(searchedUser -> friendIds.contains(searchedUser.getId()));
        exactOutput.removeIf(searchedUser -> Objects.equals(searchedUser.getId(), user.get().getId()));

//      Identifying which friends within the search have already been friended
        ArrayList<FriendRequest> existingRequests = friendRequestRepository.findFriendRequestByfromUserIdAndStatus(user.get().getId(),"PENDING");
        ArrayList<User> alreadyFriended = new ArrayList<>(exactOutput);


        ArrayList<Long> existingRequestIds = new ArrayList<>();
        for(FriendRequest friendRequest : existingRequests){
            existingRequestIds.add(friendRequest.getToUserId());
        }

        alreadyFriended.removeIf(userFriend -> !existingRequestIds.contains(userFriend.getId()));
        System.out.println(alreadyFriended);

        // Add search results to the model
        model.addAttribute("friends", exactOutput); // Replace friends list with search results
        model.addAttribute("user", user.get());
        model.addAttribute("searchQuery", searchQuery); // Keep track of the search term
        model.addAttribute("alreadyFriendRequested",alreadyFriended); // The list of people who have already been sent a friend request

        return "friends"; // Return the same friends page with search results
    }

    @PostMapping("/friends/addfriend")
    public String addFriend(@RequestParam("newFriendId") Long recipientId){
        return "redirect:/manageFriends?recipientId="+recipientId;
    }

    @PostMapping("/friends/deletefriendrequest")
    public String deleteFriendRequest(@RequestParam("friendRequestFriendId") Long friendId,@RequestParam("userId") Long userId){
        ArrayList<FriendRequest> requestsToDelete = friendRequestRepository.findFriendRequestByFromUserIdAndToUserIdAndStatus(userId,friendId,"PENDING");
        for(FriendRequest request:requestsToDelete){
            friendRequestRepository.deleteById(request.getId());
        }
        return "redirect:/friends";
    }

}
