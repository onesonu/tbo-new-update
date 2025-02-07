package com.fastays.tbo_hotel_search.dto.request;

import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseMngo;
import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseTbo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CombineResponseDTO {
    //MongoDb Data
    @JsonProperty("id")
    private String id;

    @JsonProperty("hotelCode")
    private String hotelCode;

    @JsonProperty("hotelName")
    private String hotelName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("attractions")
    private List<HotelResponseMngo.Attraction> attractions;

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

    //TboData
    @JsonProperty("Status")
    private HotelResponseTbo.Status status;

    @JsonProperty("HotelResult")
    private List<HotelResponseTbo.HotelResult> hotelResult;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Status {
        @JsonProperty("Code")
        private int code;
        @JsonProperty("Description")
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class HotelResult {
        @JsonProperty("HotelCode")
        private String hotelCode;

        @JsonProperty("Currency")
        private String currency;

        @JsonProperty("Rooms")
        private List<HotelResponseTbo.Room> rooms;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Room {
        @JsonProperty("Name")
        private List<String> name;

        @JsonProperty("BookingCode")
        private String bookingCode;

        @JsonProperty("Inclusion")
        private String inclusion;

        @JsonProperty("DayRates")
        private List<List<HotelResponseTbo.DayRate>> dayRates;

        @JsonProperty("TotalFare")
        private double totalFare;

        @JsonProperty("TotalTax")
        private double totalTax;

        @JsonProperty("RoomPromotion")
        private List<String> roomPromotion;

        @JsonProperty("CancelPolicies")
        private List<HotelResponseTbo.CancelPolicy> cancelPolicies;

        @JsonProperty("MealType")
        private String mealType;

        @JsonProperty("IsRefundable")
        private boolean isRefundable;

        @JsonProperty("Supplements")
        private List<List<HotelResponseTbo.Supplement>> supplements;

        @JsonProperty("WithTransfers")
        private boolean withTransfers;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class DayRate {
        @JsonProperty("BasePrice")
        private double basePrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class CancelPolicy {
        @JsonProperty("Index")
        private String index;

        @JsonProperty("FromDate")
        private String fromDate;

        @JsonProperty("ChargeType")
        private String chargeType;

        @JsonProperty("CancellationCharge")
        private double cancellationCharge;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Supplement {
        @JsonProperty("Index")
        private int index;

        @JsonProperty("Type")
        private String type;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("Price")
        private double price;

        @JsonProperty("Currency")
        private String currency;
    }
}
