package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "comment_likes")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "comment_id")
    private long commentId;

    @Column(name = "like_timestamp")
    private Timestamp timestamp;

    public CommentLike() {}

    public CommentLike(long userId, long commentId){
        this.userId = userId;
        this.commentId = commentId;
        this.timestamp = Timestamp.from(Instant.now());
    }
}
