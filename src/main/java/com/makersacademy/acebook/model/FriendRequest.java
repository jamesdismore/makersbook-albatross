package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name="FRIEND_REQUESTS")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="from_user_id")
    private int fromUserId;
    @Column(name="to_user_id")
    private int toUserId;
    @Column(name="friend_request_timestamp")
    private Timestamp friendRequestTimestamp;
    private String status;


    public FriendRequest(int fromUserId, int toUserId, String status){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.friendRequestTimestamp = Timestamp.from(Instant.now());
    }
}
