package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int user_id;
    private int post_id;
    private Timestamp comment_timestamp;

    public Comment(String content, int user_id, int post_id){
        this.content = content;
        this.user_id = user_id;
        this.post_id = post_id;
        this.comment_timestamp = Timestamp.from(Instant.now());
    }
}
