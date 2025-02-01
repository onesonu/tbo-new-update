package com.fastays.tbo_hotel_search.dto.request.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelResponseTbo {

    @JsonProperty("Status")
    private Status status;

    @JsonProperty("HotelResult")
    private List<HotelResult> hotelResult;

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
        private List<Room> rooms;
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
        private List<List<DayRate>> dayRates;

        @JsonProperty("TotalFare")
        private double totalFare;

        @JsonProperty("TotalTax")
        private double totalTax;

        @JsonProperty("RoomPromotion")
        private List<String> roomPromotion;

        @JsonProperty("CancelPolicies")
        private List<CancelPolicy> cancelPolicies;

        @JsonProperty("MealType")
        private String mealType;

        @JsonProperty("IsRefundable")
        private boolean isRefundable;

        @JsonProperty("Supplements")
        private List<List<Supplement>> supplements;

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
