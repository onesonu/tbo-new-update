package com.fastays.tbo_hotel_search.repository;

import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseMngo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoDbRepository extends MongoRepository<HotelResponseMngo, String> {

    // Method to find hotels by  hotel code(single)
    @Query("{ 'hotelCode' : ?0 }")
    List<HotelResponseMngo> findAllByHotelCode(String hotelCode);

    // Method to find hotels by a list of hotel codes
    @Query("{ 'hotelCode' : { $in : ?0 } }")
    List<HotelResponseMngo> findByHotelCodeIn(List<String> hotelCodes);


}


