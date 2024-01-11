package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@Api(value = "UsersController", description = "Operations pertaining to user registration and login")
@RestController
public class UsersController {

    @Autowired
    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @ApiOperation(value = "Register a new user", response = ResponseEntity.class)
    /*
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = 200, message = "Successfully registered the user"),
     * 
     * @ApiResponse(code = 401, message =
     * "Unauthorized: User limit reached or user already exists")
     * })
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {

        if (this.usersRepository.count() == 500)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User limit reached");
        else {

            Iterable<Users> users = this.usersRepository.findAll();

            for (Users user1 : users)
                if (user1.getEmail().equals(user.getEmail()))
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already exists");

            return ResponseEntity.ok(this.usersRepository.save(user));
        }
    }

    @ApiOperation(value = "Login for a user", response = ResponseEntity.class)
    /*@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in"),
            @ApiResponse(code = 401, message = "Unauthorized: Invalid credentials")
    })*/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Iterable<Users> users = this.usersRepository.findAll();

        for (Users user : users) {
            if (user.getEmail().equals(loginRequest.getEmail())
                    && user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

}