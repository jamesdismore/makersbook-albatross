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
    @Column(name="lower_user_id")
    private int lowerUserId;
    @Column(name="higher_user_id")
    private int higherUserId;
    private Timestamp friendship_timestamp;

    public Friendship(){}

    public Friendship(int lower_user_id, int higher_user_id){
        this.lowerUserId = lower_user_id;
        this.higherUserId = higher_user_id;
        this.friendship_timestamp = Timestamp.from(Instant.now());
    }
}
