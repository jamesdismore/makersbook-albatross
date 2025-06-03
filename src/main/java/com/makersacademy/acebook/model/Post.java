package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

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

    public Post() {}

    public Post(String content,int user_id, String photo) {
        this.content = content;
        this.user_id = user_id;
        this.photo = photo;
    }
}
