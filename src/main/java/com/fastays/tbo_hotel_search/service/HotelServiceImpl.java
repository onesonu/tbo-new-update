package com.fastays.tbo_hotel_search.service;

import com.fastays.tbo_hotel_search.dto.request.HotelRequest;
import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseMngo;
import com.fastays.tbo_hotel_search.dto.request.response.HotelResponseTbo;
import com.fastays.tbo_hotel_search.repository.MongoDbRepository;
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
    private final MongoDbRepository mongoDbRepository;

    public HotelServiceImpl(RestTemplate restTemplate, Configuration freemarkerConfiguration, MongoDbRepository mongoDbRepository) {
        this.restTemplate = restTemplate;
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.mongoDbRepository = mongoDbRepository;
    }

    @Override
    public String searchHotels(HotelRequest hotelRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);//setting Content-type
        headers.setBasicAuth(apiUserName, apiSecret);// providing credential
        HttpEntity<HotelRequest> entity = new HttpEntity<>(hotelRequest, headers);

        ResponseEntity<HotelResponseTbo> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, HotelResponseTbo.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            HotelResponseTbo hotelResponseTbo = response.getBody();
            // fetching data from mngo
            List<HotelResponseMngo> fetched = new ArrayList<>();
            if (hotelResponseTbo.getHotelResult() != null && !hotelResponseTbo.getHotelResult().isEmpty()) {
                fetched = fetchHotelUsingHotelCode(hotelResponseTbo);

            }
            // return mapToFtlMngo(fetched);
            return mapToFtlTbo(hotelResponseTbo);
            //  return combineResponse(hotelResponseTbo, fetched);

        } else {
            return response.getStatusCode().toString();
        }
    }

    // Mapping to FTL only Tbo Responses
    public String mapToFtlTbo(HotelResponseTbo hotelResponseTbo) {
        try {
            Template template = freemarkerConfiguration.getTemplate("hotelResponseTbo.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, hotelResponseTbo);
        } catch (Exception e) {
            String msg = e.getMessage();
            return "Error in processing response " + msg;
        }
    }

    public String mapToFtlMngo(List<HotelResponseMngo> fetched) {
        Map<String, Object> modelMngo = new HashMap<>();
        if (!fetched.isEmpty()) {
            try {
                List<Map<String, Object>> hotelResultsList = new ArrayList<>();
                Map<String, Object> mapingToModel = new HashMap<>();
                for (HotelResponseMngo hotelResponseMngo : fetched) {
                    mapingToModel.put("id", hotelResponseMngo.getId());
                    mapingToModel.put("hotelCode", hotelResponseMngo.getHotelCode());
                    mapingToModel.put("hotelName", hotelResponseMngo.getHotelName());
                    mapingToModel.put("address", hotelResponseMngo.getAddress());

                    List<Map<String, Object>> attractionsLst = new ArrayList<>();
                    for (HotelResponseMngo.Attraction attraction : hotelResponseMngo.getAttractions()) {
                        Map<String, Object> attractionMap = new HashMap<>();
                        attractionMap.put("location", attraction.getLocationName());
                        attractionMap.put("distanceInKm", attraction.getDistanceInKm());
                        attractionsLst.add(attractionMap);
                    }
                    mapingToModel.put("attractions", attractionsLst);
                    mapingToModel.put("countryName", hotelResponseMngo.getCountryName());
                    mapingToModel.put("countryCode", hotelResponseMngo.getCountryCode());
                    mapingToModel.put("description", hotelResponseMngo.getDescription());
                    mapingToModel.put("faxNumber", hotelResponseMngo.getFaxNumber());
                    mapingToModel.put("facilities", hotelResponseMngo.getHotelFacilities() != null ? String.join(", ", hotelResponseMngo.getHotelFacilities()) : "N/A");
                    mapingToModel.put("map", hotelResponseMngo.getMap());
                    mapingToModel.put("phoneNumber", hotelResponseMngo.getPhoneNumber());
                    mapingToModel.put("pinCode", hotelResponseMngo.getPinCode());
                    mapingToModel.put("websiteUrl", hotelResponseMngo.getHotelWebsiteUrl());
                    mapingToModel.put("cityName", hotelResponseMngo.getCityName());
                    mapingToModel.put("createDate", hotelResponseMngo.getCreatedDate());
                    mapingToModel.put("updateDate", hotelResponseMngo.getUpdatedDate());
                    mapingToModel.put("images", hotelResponseMngo.getImages() != null ? String.join(", ", hotelResponseMngo.getImages()) : "N/A");
                    mapingToModel.put("rating", hotelResponseMngo.getRating());
                    hotelResultsList.add(mapingToModel);

                    modelMngo.put("HotelResults", hotelResultsList);
                }
                Template template = freemarkerConfiguration.getTemplate("hotelResponseMngo.ftl");
                String processedTemp = FreeMarkerTemplateUtils.processTemplateIntoString(template, modelMngo);
                return processedTemp;
            } catch (Exception e) {
                String msg = e.getMessage();
                return "Error in processing response " + msg;
            }
        }
        return "no returns form mongo ";
    }

    //Getting Hotels from MongoDb after giving hotelCode.
    public List<HotelResponseMngo> fetchHotelUsingHotelCode(HotelResponseTbo hotelResponseTbo) {
        List<HotelResponseMngo> responseMngos;
        //getting HotelCodes
        List<String> hotelCode = new ArrayList<>();
        for (HotelResponseTbo.HotelResult hotelResult : hotelResponseTbo.getHotelResult()) {
            hotelCode.add(hotelResult.getHotelCode());
        }
        if (hotelCode.size() == 1) {
            responseMngos = mongoDbRepository.findAllByHotelCode(hotelCode.getFirst());
        } else {
            responseMngos = mongoDbRepository.findByHotelCodeIn(hotelCode);
        }
        return responseMngos;
    }

    //
    public String combineResponse(HotelResponseTbo hotelResponseTbo, List<HotelResponseMngo> fetched) {
        return null;

    }
}




