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
    private long id;
    private String content;
    private String photo;

    @Column(name = "post_timestamp")
    private Timestamp timestamp;

    public Post() {}


    // used when posting without a picture
    public Post(String content) {
        this.content = content;
        this.timestamp = Timestamp.from(Instant.now());
    }

    // posting with a picture
    public Post(String content, String photo) {
        this.content = content;
        this.timestamp = Timestamp.from(Instant.now());
        this.photo = photo;
    }


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
