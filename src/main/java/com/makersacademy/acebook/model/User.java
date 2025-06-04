package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Getter @Setter @NoArgsConstructor

@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username cannot be blank.") // error message: means we don't need to specify elsewhere that it can't be blank
    private String username;
    private boolean enabled;

    public User(String username) {
        this.username = username;
        this.enabled = TRUE;
    }
//
//    public User(String username, boolean enabled) {
//        this.username = username;
//        this.enabled = enabled;
//    }
}
