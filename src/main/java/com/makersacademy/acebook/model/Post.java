package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String photo;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "post_timestamp")
    private Timestamp timestamp;

    public Post() {}

    // used in the legacy code
    public Post(String content) {
        this.content = content;
    }

    // used when posting without a picture
    public Post(String content,int userId) {
        this.content = content;
        this.userId = userId;
        this.timestamp = Timestamp.from(Instant.now());
    }

    // posting with a picture
    public Post(String content,int userId, String photo) {
        this.content = content;
        this.userId = userId;
        this.timestamp = Timestamp.from(Instant.now());
        this.photo = photo;
    }
}
