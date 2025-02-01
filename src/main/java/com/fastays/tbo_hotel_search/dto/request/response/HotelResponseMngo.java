package com.fastays.tbo_hotel_search.dto.request.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "hotel_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseMngo {
    @Id
    private String id;
    @JsonProperty("HotelResult")
    private List<HotelResult> hotelResult;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HotelResult {

        @JsonProperty("hotelName")
        private String hotelName;

        @JsonProperty("rating")
        private String rating;

        @JsonProperty("location")
        private String location;

        @JsonProperty("amenities")
        private List<String> amenities;

        @JsonProperty("images")
        private List<String> images;

        @JsonProperty("attractions")
        private List<Attraction> attractions;

        @JsonProperty("hotelDescription")
        private String hotelDescription;

        @JsonProperty("map")
        private String map;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attraction {

        @JsonProperty("locationName")
        private String locationName;

        @JsonProperty("distanceInKm")
        private double distanceInKm;
    }
}

