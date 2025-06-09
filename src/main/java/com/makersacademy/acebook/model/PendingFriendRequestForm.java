package com.makersacademy.acebook.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PendingFriendRequestForm {

    private String firstName;

    private String lastName;

    private Long recipientId;

    private String requestMessage;

    private LocalDateTime timestamp;

    public PendingFriendRequestForm() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
