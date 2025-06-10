package com.makersacademy.acebook.model.forms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PendingFriendRequestForm {

    private String firstName;

    private String lastName;

    private Long recipientId;

    private String requestMessage;

    private LocalDateTime timestamp;

    private long requestId;

    public PendingFriendRequestForm() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
