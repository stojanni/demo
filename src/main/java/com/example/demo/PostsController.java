package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@Api(value = "PostsController", description = "Operations pertaining to posts in the application")
@RestController
public class PostsController {

    @Autowired
    private final PostsRepository postsRepository;
    @Autowired
    private FollowsRepository followsRepository;
    @Autowired
    private UsersRepository usersRepository;

    public PostsController(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @ApiOperation(value = "Create a new post", response = ResponseEntity.class)
    /*@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created the post"),
            @ApiResponse(code = 204, message = "No content, unable to create the post")
    })*/
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody Posts post) {

        Optional<Users> userOptional = this.usersRepository.findById(post.getUserId());
        Users user = userOptional.get();

        if ((user.getPlan().equals("Free") && post.getText().length() <= 1000)
                || (user.getPlan().equals("Premium") && post.getText().length() <= 3000)) {
            return ResponseEntity.ok(this.postsRepository.save(post));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Delete a post", response = ResponseEntity.class)
    @PostMapping("/deletePost")
    public BodyBuilder deletePost(@RequestBody Posts post) {
        this.postsRepository.delete(post);
        return ResponseEntity.ok();
    }

    @ApiOperation(value = "Get posts by user or their follows", response = ResponseEntity.class)
    @GetMapping("/getPosts")
    public ResponseEntity<List<Posts>> getPosts(@RequestParam Integer userId, @RequestParam String fetchType) {

        List<Posts> posts = new ArrayList<>();

        if ("my".equals(fetchType)) {
            posts = postsRepository.findByUserIdOrderByDateDesc(userId);
        } else if ("follows".equals(fetchType)) {

            List<Follows> follows = followsRepository.findByUserId(userId);

            for (Follows follow : follows) {
                List<Posts> followedUserPosts = postsRepository.findByUserIdOrderByDateDesc(follow.getFollowingId());
                posts.addAll(followedUserPosts);
            }

        }

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @ApiOperation(value = "Fetch a single post", response = ResponseEntity.class)
    @GetMapping("/getPost")
    public ResponseEntity<Posts> getPost(@RequestParam Integer postId) {
        Optional<Posts> postOptional = this.postsRepository.findById(postId);
        Posts post = postOptional.get();
        return ResponseEntity.ok(post);
    }

}