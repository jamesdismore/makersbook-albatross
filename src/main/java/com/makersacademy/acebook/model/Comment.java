package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;

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

    public Comment(String content, long userId, long postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.commentTimestamp = Timestamp.from(Instant.now());
    }

    @Override
    public String toString() {
        return String.format("USER_ID:%d;\nCONTENT:%s", this.userId, this.content);
    }
}
