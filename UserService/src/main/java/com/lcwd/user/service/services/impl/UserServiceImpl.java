package com.lcwd.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public User saveUser(User user) {
       
        //generate unique user id
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        
        return userRepository.findAll();

    }

    @Override
    public User getUser(String userId) {
        
        // get user from datavase with the help of user repository
        User user =userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
        
        //fetch rating of the above user from rating service
        //http://localhost:8087/ratings/users/b28a6fd3-c725-4c5c-a086-627453348e08
        
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+ user.getUserId() , Rating[].class );
       
        logger.info(ratingsOfUser.toString());

        List<Rating> ratings =  Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/b28a6fd3-c725-4c5c-a086-627453348e08
             
           // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
        
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
        
            //logger.info("response status code: " + forEntity.getStatusCode());
            //set the hotel to rating
            
            rating.setHotel(hotel);
           
            return rating;
        
        }).collect(Collectors.toList());
 
        user.setRatings(ratingList);

        return user;
    }

    @Override
    public User deleteUser(String userId) {
       
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
        userRepository.delete(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User user1= userRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !! : " + user.getUserId()));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setAbout(user.getAbout());
        return userRepository.save(user1);

    }



}
