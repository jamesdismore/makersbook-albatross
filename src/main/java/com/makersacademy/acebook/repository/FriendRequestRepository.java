package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.FriendRequest;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    public ArrayList<FriendRequest> findFriendRequestByfromUserIdAndStatus(long fromUserId,String status);
    public ArrayList<FriendRequest> findFriendRequestBytoUserIdAndStatus(long fromUserId,String status);
}
