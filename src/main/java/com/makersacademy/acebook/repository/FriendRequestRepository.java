package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.FriendRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    public ArrayList<FriendRequest> findFriendRequestByfromUserIdAndStatus(int fromUserId,String status);
    public ArrayList<FriendRequest> findFriendRequestBytoUserIdAndStatus(int toUserId, String status);
}
