package com.makersacademy.acebook.model;


import com.makersacademy.acebook.model.forms.FriendRequestResponseForm;
import com.makersacademy.acebook.model.forms.IncomingFriendRequestForm;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="user_id")
      
    private long userId;
    @Column(name="friend_id")
    private long friendId;
    private Timestamp friendshipTimestamp;

    public Friendship(){}

    public Friendship(long userId, long friendId){
        this.userId = userId;
        this.friendId = friendId;
        this.friendshipTimestamp = Timestamp.from(Instant.now());
    }

    public static Friendship[] fromForm(IncomingFriendRequestForm form){
        Friendship aToB = new Friendship();
        aToB.setUserId(form.getRecipientId());
        aToB.setFriendId(form.getSenderId());
        aToB.setFriendshipTimestamp(Timestamp.from(Instant.now()));

        Friendship bToA = new Friendship();
        bToA.setUserId(form.getRecipientId());
        bToA.setFriendId(form.getSenderId());
        bToA.setFriendshipTimestamp(Timestamp.from(Instant.now()));

        Friendship[] friendships = new Friendship[2];
        friendships[0] = aToB;
        friendships[1] = bToA;

        return friendships;
    }
}
