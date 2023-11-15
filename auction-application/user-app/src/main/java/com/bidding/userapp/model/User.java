package com.bidding.userapp.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    private String name;

    private String email;

    private String password;

    public User(){

    }

    public User(String name, String email, String password){
        this.name= name;
        this.email=email;
        this.password=password;
    }

}
