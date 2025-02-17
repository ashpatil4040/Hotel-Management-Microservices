package com.lcwd.hotel.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.hotel.entites.Hotel;
import com.lcwd.hotel.exceptions.ResourcNotFoundException;
import com.lcwd.hotel.repositories.HotelRepository;
import com.lcwd.hotel.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {

         String hotelId=UUID.randomUUID().toString();
         hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
       
        return hotelRepository.findById(id).orElseThrow(() -> new ResourcNotFoundException("Hotel with given id not found !!"));
    }

    @Override
    public Hotel update(Hotel hotel) {
      
       Hotel hotel1= hotelRepository.findById(hotel.getId()).orElseThrow(() -> new ResourcNotFoundException("Hotel with given id not found !!"));
       hotel1.setName(hotel.getName());
       hotel1.setLocation(hotel.getLocation());
       hotel.setAbout(hotel.getAbout());
       return hotelRepository.save(hotel1);

    }

    @Override
    public Hotel delete(String id) {
       
        Hotel hotel1= hotelRepository.findById(id).orElseThrow(() -> new ResourcNotFoundException("Hotel with given id not found !!"));
        hotelRepository.deleteById(id);
        return hotel1;
    }

}
