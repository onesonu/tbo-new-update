package com.fastays.tbo_hotel_search.service;

import com.fastays.tbo_hotel_search.dto.request.CombineResponseDTO;
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

            return mapToFtlMngo(fetched);
            //return mapToFtlTbo(hotelResponseTbo);
            //return combineResponse(hotelResponseTbo, fetched);

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
        if (!fetched.isEmpty()) {
            try {
                Map<String, Object> mapingToModel = new HashMap<>();
                mapingToModel.put("results", fetched);

                Template template = freemarkerConfiguration.getTemplate("hotelResponseMngo.ftl");
                return FreeMarkerTemplateUtils.processTemplateIntoString(template, mapingToModel);
            } catch (Exception e) {
                String msg = e.getMessage();
                return "Error in processing response " + msg;
            }
        }
        return "No Response From MongoDB ";
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
        CombineResponseDTO combineResponseDTO = new CombineResponseDTO();
        if (!fetched.isEmpty()) {
            for (HotelResponseMngo hotelResponseMngo : fetched) {
                combineResponseDTO.setId(hotelResponseMngo.getId());
                combineResponseDTO.setHotelCode(hotelResponseMngo.getHotelCode());
                combineResponseDTO.setHotelName(hotelResponseMngo.getHotelName());
                combineResponseDTO.setAddress(hotelResponseMngo.getAddress());
                combineResponseDTO.setAttractions(hotelResponseMngo.getAttractions());
                combineResponseDTO.setCountryName(hotelResponseMngo.getCountryName());
                combineResponseDTO.setCountryCode(hotelResponseMngo.getCountryCode());
                combineResponseDTO.setDescription(hotelResponseMngo.getDescription());
                combineResponseDTO.setFaxNumber(hotelResponseMngo.getFaxNumber());
                combineResponseDTO.setHotelFacilities(hotelResponseMngo.getHotelFacilities());
                combineResponseDTO.setMap(hotelResponseMngo.getMap());
                combineResponseDTO.setPhoneNumber(hotelResponseMngo.getPhoneNumber());
                combineResponseDTO.setPinCode(hotelResponseMngo.getPinCode());
                combineResponseDTO.setHotelWebsiteUrl(hotelResponseMngo.getHotelWebsiteUrl());
                combineResponseDTO.setCityName(hotelResponseMngo.getCityName());
                combineResponseDTO.setCreatedDate(hotelResponseMngo.getCreatedDate());
                combineResponseDTO.setUpdatedDate(hotelResponseMngo.getUpdatedDate());
                combineResponseDTO.setImages(hotelResponseMngo.getImages());
                combineResponseDTO.setRating(hotelResponseMngo.getRating());
                combineResponseDTO.set_class(hotelResponseMngo.get_class());
            }
            combineResponseDTO.setStatus(hotelResponseTbo.getStatus());
            combineResponseDTO.setHotelResult(hotelResponseTbo.getHotelResult());
        }
        System.out.println(combineResponseDTO);

        return "sonu";

    }
}




