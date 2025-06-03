package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private boolean enabled;
    private String avatar;
    private LocalDate dob;
    private String first_name;
    private String last_name;

    public User() {
        this.enabled = TRUE;
    }


    //used in legacy code
    public User(String username) {
        this.username = username;
//        this.avatar = avatar;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.dob = dob;
        this.enabled = TRUE;
    }
    public User(String username, boolean enabled) {
        this.username = username;
        this.enabled = enabled;
    }

    // used - assumes there will be an avatar
    public User(String username,String first_name, String last_name, LocalDate dob, String avatar) {
        this.username = username;
        this.avatar = avatar;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.avatar = avatar;
        this.enabled = TRUE;
    }


}
