package com.makersacademy.acebook.model.forms;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IncomingFriendRequestsWrapper {

    private final List<IncomingFriendRequestForm> incomingFriendRequests;

    public void addIncomingFriendRequest(IncomingFriendRequestForm incomingFriendRequestForm) {
        this.incomingFriendRequests.add(incomingFriendRequestForm);
    }

    public IncomingFriendRequestsWrapper() {
        incomingFriendRequests = new ArrayList<IncomingFriendRequestForm>();
    }

    public IncomingFriendRequestForm formAtIndex(Integer index) {
        return incomingFriendRequests.get(index);
    }

}
