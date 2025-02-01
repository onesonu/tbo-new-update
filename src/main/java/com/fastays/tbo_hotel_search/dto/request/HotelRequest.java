package com.fastays.tbo_hotel_search.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {
    public String CheckIn;
    public String CheckOut;
    public Filters Filters;
    public String HotelName;
    public int MealType;
    public int NoOfRooms;
    public int OrderBy;
    public boolean Refundable;
    public int StarRating;
    public String GuestNationality;
    public String HotelCodes;
    public boolean IsDetailedResponse;
    public List<PaxRoom> PaxRooms;
    public int ResponseTime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filters {
        public boolean Refundable;
        public int NoOfRooms;
        public int MealType;
        public int OrderBy;
        public int StarRating;
        public String HotelName;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaxRoom {
        public int Adults;
        public int Children;
        public List<Integer> ChildrenAges;
    }
}




