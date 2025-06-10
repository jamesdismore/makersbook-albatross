package com.makersacademy.acebook.model.forms;

import com.makersacademy.acebook.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FriendRequestForm {

    // Set before template
    private long senderId;
    private long recipientId;
    private String recipientFirstName;

    // Set within template
    private String message;
    private LocalDate timestamp;

    public FriendRequestForm(User sender, User recipient) {
        this.senderId = sender.getId();
        this.recipientId = recipient.getId();
        this.recipientFirstName = recipient.getFirstName();
    }
}
