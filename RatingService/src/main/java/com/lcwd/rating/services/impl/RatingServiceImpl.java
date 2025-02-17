package com.lcwd.rating.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.repository.RatingRepository;
import com.lcwd.rating.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

   @Autowired
   private RatingRepository ratingRepository;   

    @Override
    public Rating create(Rating rating) {

        return ratingRepository.save(rating);
      
    }

    @Override
    public List<Rating> getRatings() {
        
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
       
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
       
        return ratingRepository.findByHotelId(hotelId);
    }

    @Override
    public Rating updateRating(Rating rating) {
        Rating rating1 = ratingRepository.findById(rating.getRatingId()).orElseThrow(() -> new RuntimeException("Rating not found"));
        rating1.setFeedback(rating.getFeedback());
        rating1.setRating(rating.getRating());
        rating1.setUserId(rating.getUserId());
        rating1.setHotelId(rating.getHotelId());
        return ratingRepository.save(rating1);
    }

    @Override
    public Rating deleteRating(String ratingId) {
       
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new RuntimeException("Rating not found"));

        ratingRepository.deleteById(ratingId);
        return rating;
    }

}
