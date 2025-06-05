package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Friendship;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    public ArrayList<Friendship> findFriendshipById(int id);
    public ArrayList<Friendship> findFriendshipByuserId(int userId);
}
