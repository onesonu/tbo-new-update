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
            //return mapToFtlTbo(hotelResponseTbo);
            //return mapToFtlMngo(fetched);
            return combineResponse(hotelResponseTbo, fetched);

        } else {
            return response.getStatusCode().toString();
        }
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

    // mapping Both the Response
    public String combineResponse(HotelResponseTbo hotelResponseTbo, List<HotelResponseMngo> fetched) {
        if (!fetched.isEmpty()) {
            try {
                Map<String, Object> mapingToModel = new HashMap<>();
                mapingToModel.put("resultsMngo", fetched);
                mapingToModel.put("resultsTbo", hotelResponseTbo);

                Template template = freemarkerConfiguration.getTemplate("combineResponse.ftl");
                return FreeMarkerTemplateUtils.processTemplateIntoString(template, mapingToModel);
            } catch (Exception e) {
                String msg = e.getMessage();
                return "Error in processing response " + msg;
            }
        }
        return "Error in fetching the data and Mapping the Combine Response ";
    }
}




