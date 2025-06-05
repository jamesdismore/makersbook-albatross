package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "FRIENDSHIPS")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private int userId;
    @Column(name="friend_id")
    private int FriendId;
    private Timestamp friendship_timestamp;

    public Friendship(){}

    public Friendship(int userId, int friendId){
        this.userId = userId;
        this.FriendId = friendId;
        this.friendship_timestamp = Timestamp.from(Instant.now());
    }
}
