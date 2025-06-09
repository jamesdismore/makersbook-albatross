package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Friendship;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    public ArrayList<Friendship> findFriendshipById(int id);
    public ArrayList<Friendship> findFriendshipByUserId(int userId);
    public ArrayList<Friendship> findFriendshipByFriendId(int friendId);
    public ArrayList<Friendship> findFriendshipByUserIdOrFriendId(int userId, int friendId);
}
