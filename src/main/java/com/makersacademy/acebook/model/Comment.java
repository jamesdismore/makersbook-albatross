package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "comment_timestamp")
    private Timestamp commentTimestamp;

    public Comment() {
        this.commentTimestamp = Timestamp.from(Instant.now());
    }

    public Comment(String content, long userId, long postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.commentTimestamp = Timestamp.from(Instant.now());
    }

    // used when posting without a picture
    public Comment(String content) {
        this.content = content;
        this.commentTimestamp = Timestamp.from(Instant.now());
    }

}
