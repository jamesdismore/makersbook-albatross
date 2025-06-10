package com.makersacademy.acebook.model;

import com.makersacademy.acebook.model.forms.FriendRequestForm;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name="friend_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="from_user_id")
    private long fromUserId;
    @Column(name="to_user_id")
    private long toUserId;

    @Column(name="request_timestamp")
    private Timestamp requestTimestamp;

    @Column(name="request_message")
    private String requestMessage;

    @Column(name = "response_timestamp")
    private Timestamp responseTimestamp;

    @Column(name = "response_message")
    private String responseMessage;

    private String status;

    public FriendRequest(){}

    public FriendRequest(int fromUserId, int toUserId, String status, String requestMessage){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.requestMessage = requestMessage;
        this.requestTimestamp = Timestamp.from(Instant.now());
    }

    public static FriendRequest fromForm(FriendRequestForm friendRequestForm) {
        FriendRequest friendRequest = new  FriendRequest();
        friendRequest.setRequestMessage(friendRequestForm.getMessage());
        friendRequest.setRequestTimestamp(Timestamp.from(Instant.now()));
        friendRequest.setFromUserId(friendRequestForm.getSenderId());
        friendRequest.setToUserId(friendRequestForm.getRecipientId());
        friendRequest.setStatus("PENDING");
        return friendRequest;
    }
}
