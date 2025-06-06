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

    @Column(name = "lower_user_id")
    private long lowerUserId;

    @Column(name = "higher_user_id")
    private long higherUserId;
    private Timestamp friendshipTimestamp;

    public Friendship(long lowerUserId, long higherUserId){
        this.lowerUserId = lowerUserId;
        this.higherUserId = higherUserId;
        this.friendshipTimestamp = Timestamp.from(Instant.now());
    }

    public Friendship() {

    }
}
