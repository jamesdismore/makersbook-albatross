package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="user_id")
      
    private long userId;
    @Column(name="friend_id")
    private long FriendId;
    private Timestamp friendshipTimestamp;

    public Friendship(){}

    public Friendship(long userId, long friendId){
        this.userId = userId;
        this.FriendId = friendId;
        this.friendshipTimestamp = Timestamp.from(Instant.now());
    }
}
