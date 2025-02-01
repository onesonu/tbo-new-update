package com.fastays.tbo_hotel_search.controller;

import com.fastays.tbo_hotel_search.dto.request.HotelRequest;
import com.fastays.tbo_hotel_search.dto.request.response.HotelResponse;
import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseTbo;
import com.fastays.tbo_hotel_search.service.HotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/hotels")

public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    //http://localhost:8080/api/hotels/search
    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestBody HotelRequest hotelRequest, Model model) {

        try {
            String hotelResponse = hotelService.searchHotels(hotelRequest);
            model.addAttribute("hotelResponse", hotelResponse);
            return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred while processing the hotel search: " + e.getMessage());
        }

    }
}