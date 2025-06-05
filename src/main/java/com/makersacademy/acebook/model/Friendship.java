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
    private Long id;

    @Column(name = "lower_user_id")
    private int lowerUserId;

    @Column(name = "higher_user_id")
    private int higherUserId;
    private Timestamp friendshipTimestamp;

    public Friendship(int lowerUserId, int higherUserId){
        this.lowerUserId = lowerUserId;
        this.higherUserId = higherUserId;
        this.friendshipTimestamp = Timestamp.from(Instant.now());
    }
}
