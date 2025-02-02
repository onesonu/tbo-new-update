package com.fastays.tbo_hotel_search.service;

import com.fastays.tbo_hotel_search.dto.request.HotelRequest;

public interface HotelService {
    String searchHotels(HotelRequest hotelRequest);
}
