package com.fastays.tbo_hotel_search.dto.request.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelResponse {
    private Status Status;
    private List<HotelResult> HotelResult;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private int Code;
        private String Description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HotelResult {
        private String HotelCode;
        private String Currency;
        private List<Room> Rooms;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Room {
        private List<String> Name;
        private String BookingCode;
        private String Inclusion;
        private List<List<DayRate>> DayRates;
        private double TotalFare;
        private double TotalTax;
        private List<String> RoomPromotion;
        private List<CancelPolicy> CancelPolicies;
        private String MealType;
        private boolean IsRefundable;
        private boolean WithTransfers;
        //this field not present in response
        private List<List<Supplement>> Supplements;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DayRate {
        private double BasePrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CancelPolicy {
        private String Index;
        private String FromDate;
        private String ChargeType;
        private int CancellationCharge;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Supplement {
        private int Index;
        private String Type;
        private String Description;
        private double Price;
        private String Currency;
    }

}