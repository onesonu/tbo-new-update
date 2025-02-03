package com.fastays.tbo_hotel_search.repository;

import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseMngo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MondoDBRepository extends MongoRepository<HotelResponseMngo, String> {

    // Method to find hotels by a list of hotel codes
    @Query("{ 'hotelCode' : { $in : ?0 } }")
    List<HotelResponseMngo> findByHotelCodeIn(List<String> hotelCodes);



}


