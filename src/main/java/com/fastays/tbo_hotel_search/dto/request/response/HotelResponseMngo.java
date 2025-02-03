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

    @JsonProperty("hotelCode")
    private String hotelCode;

    @JsonProperty("hotelName")
    private String hotelName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("attractions")
    private List<Attraction> attractions;

    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("description")
    private String description;

    @JsonProperty("faxNumber")
    private String faxNumber;

    @JsonProperty("hotelFacilities")
    private List<String> hotelFacilities;

    @JsonProperty("map")
    private String map;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("pinCode")
    private String pinCode;

    @JsonProperty("hotelWebsiteUrl")
    private String hotelWebsiteUrl;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("createdDate")
    private String createdDate;

    @JsonProperty("updatedDate")
    private String updatedDate;

    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("_class")
    private String _class;

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
