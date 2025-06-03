package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name = "POST_LIKES")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int user_id;
    private int post_id;
    private Timestamp like_timestamp;

    public PostLike(int user_id, int post_id){
        this.user_id = user_id;
        this.post_id = post_id;
        this.like_timestamp = Timestamp.from(Instant.now());
    }
}
