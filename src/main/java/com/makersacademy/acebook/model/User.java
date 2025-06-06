package com.makersacademy.acebook.model;

import com.makersacademy.acebook.model.validation.DateOfBirthNotNullAndOver14Constraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Getter @Setter

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank.") // error message: means we don't need to specify elsewhere that it can't be blank
    private String username;
    private boolean enabled;
    private String avatar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateOfBirthNotNullAndOver14Constraint
    private LocalDate dob;

    @NotBlank(message = "Please enter your first name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Please enter your last name")
    @Column(name = "last_name")
    private String lastName;

    public User() {
        this.enabled = TRUE;
    }

    //used in legacy code
    public User(String username) {
        this.username = username;
        this.enabled = TRUE;
    }

    // used - assumes there will be an avatar
    public User(String username,String firstName, String lastName, LocalDate dob, String avatar) {
        this.username = username;
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.avatar = avatar;
        this.enabled = TRUE;
    }

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
