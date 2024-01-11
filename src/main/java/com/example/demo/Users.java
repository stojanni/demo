package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String email;
    private String password;
    private String plan;

    private Users() {
    }

    public Users(String email, String password, String plan) {
        this.email = email;
        this.password = password;
        this.plan = plan;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPlan() {
        return this.plan;
    }
}