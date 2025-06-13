package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.FriendRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    public ArrayList<FriendRequest> findFriendRequestByfromUserIdAndStatus(long fromUserId,String status);
    public ArrayList<FriendRequest> findFriendRequestBytoUserIdAndStatus(long fromUserId,String status);
    public ArrayList<FriendRequest> findFriendRequestByFromUserIdAndToUserIdAndStatus (long fromUserId,long toUserId, String status);
    public ArrayList<FriendRequest> findFriendRequestByFromUserIdOrToUserId(long fromUserId, long toUserId);
}
