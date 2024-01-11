package com.example.demo;

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

@Api(value = "CommentsController", description = "Operations pertaining to comments in the application")
@RestController
public class CommentsController {

    @Autowired
    private final CommentsRepository commentsRepository;
    @Autowired
    private UsersRepository usersRepository;

    public CommentsController(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @ApiOperation(value = "Create a comment", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message = "Successfully created the comment"),
     * 
     * @ApiResponse(code = 204, message =
     * "No content, unable to create the comment due to restrictions")
     * })
     */
    @PostMapping("/comment")
    public ResponseEntity<Comments> comment(@RequestBody Comments comment) {

        Long count = commentsRepository.countByUserIdAndPostId(comment.getUserId(), comment.getPostId());
        Optional<Users> userOptional = this.usersRepository.findById(comment.getUserId());
        Users user = userOptional.get();

        if ((user.getPlan().equals("free") && count < 5) || user.getPlan().equals("premium")) {
            return ResponseEntity.ok(this.commentsRepository.save(comment));
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @ApiOperation(value = "Delete a comment", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message = "Successfully deleted the comment")
     * })
     */
    @PostMapping("/deleteComment")
    public BodyBuilder deleteComment(@RequestBody Comments comment) {
        this.commentsRepository.delete(comment);
        return ResponseEntity.ok();
    }

    @ApiOperation(value = "Get all comments of a post", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message = "Successfully retrieved comments"),
     * 
     * @ApiResponse(code = 204, message =
     * "No content, no comments found for the post")
     * })
     */
    @GetMapping("/getComments")
    public ResponseEntity<List<Comments>> getComments(@RequestParam Integer postId) {

        List<Comments> comments = commentsRepository.findByPostIdOrderByDateDesc(postId);

        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comments);
    }

}