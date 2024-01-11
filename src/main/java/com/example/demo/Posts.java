package com.example.demo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;

    private Integer userId;
    private String text;
    private Date date;

    private Posts() {
    }

    public Posts(Integer userId, String text, Date date) {
        this.userId = userId;
        this.text = text;
        this.date = date;
    }

    public Integer getPostId() {
        return this.postId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getText() {
        return this.text;
    }

    public Date getDate() {
        return this.date;
    }
}