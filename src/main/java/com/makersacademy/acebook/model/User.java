package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Getter @Setter

@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank.") // error message: means we don't need to specify elsewhere that it can't be blank
    private String username;
    private boolean enabled;
    private String avatar;

    @NotNull(message = "Please set your date of birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message = "Please enter your first name")
    private String first_name;

    @NotBlank(message = "Please enter your last name")
    private String last_name;

    public User() {
        this.enabled = TRUE;
    }

    //used in legacy code
    public User(String username) {
        this.username = username;
        this.enabled = TRUE;
    }

//
//    public User(String username, boolean enabled) {
//        this.username = username;
//        this.enabled = enabled;
//    }

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
