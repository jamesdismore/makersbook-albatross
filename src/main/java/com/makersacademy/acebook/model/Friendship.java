package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "FRIENDSHIPS")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lower_user_id;
    private int higher_user_id;

    public Friendship(int lower_user_id, int higher_user_id){
        this.lower_user_id = lower_user_id;
        this.higher_user_id = higher_user_id;
    }
}
