package com.lcwd.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);


    //create

    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
            
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);

    }
    //get single user
    int retryCount = 1;
    @GetMapping("/{userId}")
    // @CircuitBreaker(name = "RatingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
 
        logger.info("Retry count : " + retryCount);
        retryCount++;

       User user= userService.getUser(userId);
       return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //creating fallback method for circuitbreaker
    

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        //logger.info("Fallback is executed because service is down : ",ex.getMessage());
        
        User user = User.builder()
                        .email("dummy@gmail.com")
                        .name("Dummy")
                        .about("This user is created dummy because some service is down")
                        .userId("141234")
                        .build();
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    //delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        User user = userService.deleteUser(userId);
        return ResponseEntity.ok(user);
    }

    //update user
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user){
        user.setUserId(userId);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
}
