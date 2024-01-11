package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

    //Retrieves a post from its id in reverse chronological order
    List<Comments> findByPostIdOrderByDateDesc(Integer postId);

    //Retrieves the number of comments a user has posted under a post
    @Query("SELECT COUNT(c) FROM Comments c WHERE c.userId = :userId AND c.postId = :postId")
    Long countByUserIdAndPostId(Integer userId, Integer postId);
}