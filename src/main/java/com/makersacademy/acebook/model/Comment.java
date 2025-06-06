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
    private int userId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "comment_timestamp")
    private Timestamp commentTimestamp;

    public Comment(String content, int userId, int postId){
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.commentTimestamp = Timestamp.from(Instant.now());
    }
}
