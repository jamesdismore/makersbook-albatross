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
    @Column(name="request_timestamp")
    private Timestamp requestTimestamp;

    @Column(name="request_message")
    private String requestMessage;
    private String status;

    public FriendRequest(){}

    public FriendRequest(int fromUserId, int toUserId, String status, String requestMessage){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.requestMessage = requestMessage;
        this.requestTimestamp = Timestamp.from(Instant.now());
    }
}
