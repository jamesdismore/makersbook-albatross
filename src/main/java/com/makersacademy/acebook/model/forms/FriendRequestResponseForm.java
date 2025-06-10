package com.makersacademy.acebook.model.forms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendRequestResponseForm {

    private String status;

    private long requestId;

    private String firstName;
    private String lastName;

    private Long recipientId;
    private Long senderId;

    private String message;
    private LocalDateTime timestamp;


    public FriendRequestResponseForm() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}


