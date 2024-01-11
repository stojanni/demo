package com.example.demo;

import java.util.List;

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

@Api(value = "FollowsController", description = "Operations pertaining to user follows in the application")
@RestController
public class FollowsController {

    @Autowired
    private final FollowsRepository followsRepository;

    public FollowsController(FollowsRepository followsRepository) {
        this.followsRepository = followsRepository;
    }

    @ApiOperation(value = "Follow a user", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message = "Successfully followed the user")
     * })
     */
    @PostMapping("/follow")
    public ResponseEntity<Follows> follow(@RequestBody Follows follow) {
        return ResponseEntity.ok(this.followsRepository.save(follow));
    }

    @ApiOperation(value = "Unfollow a user", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message = "Successfully unfollowed the user")
     * })
     */
    @PostMapping("/unfollow")
    public BodyBuilder unfollow(@RequestBody Follows follow) {
        this.followsRepository.delete(follow);
        return ResponseEntity.ok();
    }

    @ApiOperation(value = "Get all users a user follows", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message =
     * "Successfully retrieved the follows list"),
     * 
     * @ApiResponse(code = 204, message =
     * "No content, the user does not follow anyone")
     * })
     */
    @GetMapping("/getFollows")
    public ResponseEntity<List<Follows>> getFollows(@RequestParam Integer userId) {

        List<Follows> followedUserIds = followsRepository.findByUserId(userId);

        if (followedUserIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(followedUserIds);
    }

}