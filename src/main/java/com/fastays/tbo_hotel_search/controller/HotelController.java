package com.fastays.tbo_hotel_search.controller;

import com.fastays.tbo_hotel_search.dto.request.HotelRequest;
import com.fastays.tbo_hotel_search.exception.HotelSearchException;
import com.fastays.tbo_hotel_search.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")

public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    //http://localhost:8080/api/hotels/search
    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestBody HotelRequest hotelRequest) {
        try {
            String hotelResponse = hotelService.searchHotels(hotelRequest);
            return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
        } catch (HotelSearchException e) {
            return ResponseEntity.badRequest().body("Error occurred while processing the hotel search: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

}