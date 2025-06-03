package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "COMMENT_LIKES")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int user_id;
    private int comment_id;
    private Timestamp like_timestamp;

    public CommentLike(int user_id, int comment_id){
        this.user_id = user_id;
        this.comment_id = comment_id;
        this.like_timestamp = Timestamp.from(Instant.now());
    }
}
