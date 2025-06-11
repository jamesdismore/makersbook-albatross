package com.makersacademy.acebook.model.forms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncomingFriendRequestForm {

    private long senderId;
    private long recipientId;
    private long requestId;

    private String senderName;
    private String message;
    private String response;
    private LocalDateTime requestTimestamp;


}
