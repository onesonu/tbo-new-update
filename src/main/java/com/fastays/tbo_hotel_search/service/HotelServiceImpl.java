package com.fastays.tbo_hotel_search.service;

import com.fastays.tbo_hotel_search.dto.request.HotelRequest;
import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseTbo;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import freemarker.template.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class HotelServiceImpl implements HotelService {
    @Value("${tbo.api.url}")
    private String apiUrl;
    @Value("${tbo.api.username}")
    private String apiUserName;
    @Value("${tbo.api.secret}")
    private String apiSecret;

    private final RestTemplate restTemplate;
    private final Configuration freemarkerConfiguration;

    public HotelServiceImpl(RestTemplate restTemplate, Configuration freemarkerConfiguration) {
        this.restTemplate = restTemplate;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public String searchHotels(HotelRequest hotelRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);//setting Content-type
        headers.setBasicAuth(apiUserName, apiSecret);// providing credential

        HttpEntity<HotelRequest> entity = new HttpEntity<>(hotelRequest, headers);

        ResponseEntity<HotelResponseTbo> response =
                restTemplate.exchange(apiUrl, HttpMethod.POST, entity, HotelResponseTbo.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            HotelResponseTbo hotelResponseTbo = response.getBody();
            //calling the method which bind the data to FTL file
            String ftl = mapToFtl(hotelResponseTbo);
            return ftl;
        } else {
            return response.getStatusCode().toString();
        }
    }

    // this code is for mapping to ftl file
    public String mapToFtl(HotelResponseTbo hotelResponseTbo) {
        try {
            Map<String, Object> model = new HashMap<>();
            HotelResponseTbo.Status sts = hotelResponseTbo.getStatus();
            model.put("Status", sts);

            if (sts.getCode() != 200) { // assuming a non-200 code indicates error
                Map<String, Object> errorMapforsts = new HashMap<>();
                errorMapforsts.put("errorCode", sts.getCode());
                errorMapforsts.put("errorMessage", sts.getDescription());
                model.put("error", errorMapforsts);
            }

            if (hotelResponseTbo.getHotelResult() != null && !hotelResponseTbo.getHotelResult().isEmpty()) {
                List<Map<String, Object>> hotelResultsList = new ArrayList<>();

                for (HotelResponseTbo.HotelResult hotelResult : hotelResponseTbo.getHotelResult()) {
                    Map<String, Object> hotelMap = new HashMap<>();
                    hotelMap.put("hotelCode", hotelResult.getHotelCode());
                    hotelMap.put("currency", hotelResult.getCurrency());
                    //rooms
                    List<Map<String, Object>> roomList = new ArrayList<>();
                    for (HotelResponseTbo.Room room : hotelResult.getRooms()) {
                        Map<String, Object> roomMap = new HashMap<>();
                        roomMap.put("name", room.getName() != null ? String.join(", ", room.getName()) : "N/A");
                        roomMap.put("bookingCode", room.getBookingCode() != null ? room.getBookingCode() : "N/A");
                        roomMap.put("inclusion", room.getInclusion() != null ? room.getInclusion() : "N/A");

                        // DayRates
                        List<Map<String, Object>> dayRatesList = new ArrayList<>();
                        for (List<HotelResponseTbo.DayRate> dayRateGroup : room.getDayRates()) {
                            for (HotelResponseTbo.DayRate dayRate : dayRateGroup) {
                                Map<String, Object> dayRateMap = new HashMap<>();
                                dayRateMap.put("basePrice", dayRate.getBasePrice());
                                dayRatesList.add(dayRateMap);
                            }
                        }

                        roomMap.put("dayRates", dayRatesList);
                        // Additional properties for room
                        roomMap.put("totalFare", room.getTotalFare());
                        roomMap.put("totalTax", room.getTotalTax());
                        roomMap.put("roomPromotion", room.getRoomPromotion() != null ? String.join(", ", room.getRoomPromotion()) : "N/A");
                        // Cancel policies
                        List<Map<String, Object>> cancelPoliciesList = new ArrayList<>();
                        for (HotelResponseTbo.CancelPolicy cancelPolicy : room.getCancelPolicies()) {
                            Map<String, Object> cancelPolicyMap = new HashMap<>();
                            cancelPolicyMap.put("index", cancelPolicy.getIndex() != null ? cancelPolicy.getIndex() : "N/A");
                            cancelPolicyMap.put("fromDate", cancelPolicy.getFromDate() != null ? cancelPolicy.getFromDate() : "N/A");
                            cancelPolicyMap.put("chargeType", cancelPolicy.getChargeType() != null ? cancelPolicy.getChargeType() : "N/A");
                            cancelPolicyMap.put("cancellationCharge", cancelPolicy.getCancellationCharge());
                            cancelPoliciesList.add(cancelPolicyMap);
                        }
                        roomMap.put("cancelPolicies", cancelPoliciesList);
                        // Supplements
                        List<Map<String, Object>> supplementsList = new ArrayList<>();
                        if (room.getSupplements() != null) {
                            for (List<HotelResponseTbo.Supplement> supplementGroup : room.getSupplements()) {
                                for (HotelResponseTbo.Supplement supplement : supplementGroup) {
                                    Map<String, Object> supplementMap = new HashMap<>();
                                    supplementMap.put("index", supplement.getIndex());
                                    supplementMap.put("type", supplement.getType() != null ? supplement.getType() : "N/A");
                                    supplementMap.put("description", supplement.getDescription() != null ? supplement.getDescription() : "N/A");
                                    supplementMap.put("price", supplement.getPrice());
                                    supplementMap.put("currency", supplement.getCurrency() != null ? supplement.getCurrency() : "N/A");
                                    supplementsList.add(supplementMap);
                                }
                            }
                        }
                        roomMap.put("supplements", supplementsList);
                        // Additional room information
                        roomMap.put("mealType", room.getMealType() != null ? room.getMealType() : "N/A");
                        roomMap.put("isRefundable", room.isRefundable() ? "true" : "false");
                        roomMap.put("withTransfers", room.isWithTransfers() ? "true" : "false");
                        roomList.add(roomMap);
                    }
                    hotelMap.put("rooms", roomList);

                    hotelResultsList.add(hotelMap);
                }
                model.put("hotelResults", hotelResultsList);
            }
            Template template = freemarkerConfiguration.getTemplate("hotelResults.ftl");
            String processedTemp = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return processedTemp;
        } catch (Exception e) {
            String msg = e.getMessage();
            return "Error in processing response " + msg;
        }

    }


}



