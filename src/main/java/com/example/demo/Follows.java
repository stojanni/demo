package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer followId;

    private Integer userId;
    private Integer followingId;

    private Follows() {
    }

    public Follows(Integer userId, Integer followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }

    public Integer getFollowId() {
        return this.followId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Integer getFollowingId() {
        return this.followingId;
    }
}