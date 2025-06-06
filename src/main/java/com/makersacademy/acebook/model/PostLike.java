package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "like_timestamp")
    private Timestamp timestamp;

    public PostLike(long user_id, long post_id){
        this.userId = user_id;
        this.postId = post_id;
        this.timestamp = Timestamp.from(Instant.now());
    }

    public PostLike() {

    }
}
