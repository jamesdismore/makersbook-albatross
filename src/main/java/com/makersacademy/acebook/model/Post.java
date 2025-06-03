package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String photo;
    private int user_id;
    private Timestamp post_timestamp;

    public Post() {}

    // used in the legacy code
    public Post(String content) {
        this.content = content;
    }

    // used when posting without a picture
    public Post(String content,int user_id) {
        this.content = content;
        this.user_id = user_id;
        this.post_timestamp = Timestamp.from(Instant.now());
    }

    // posting with a picture
    public Post(String content,int user_id,String photo) {
        this.content = content;
        this.user_id = user_id;
        this.post_timestamp = Timestamp.from(Instant.now());
        this.photo = photo;
    }
}
