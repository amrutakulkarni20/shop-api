package com.shop.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiClient
{
    @Value("${api.key}")
    private String apiKey;

    @Value("${token.api.url}")
    private String apiUrl;


    public String getAccountToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = createRequestBody();
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
        String accessToken = (String) response.getBody().get("access_token");
        return accessToken;
    }

//        String accessToken = (String) response.getBody().get("access_token");
//
//        HttpHeaders apiHeaders = new HttpHeaders();
//        apiHeaders.setBearerAuth(accessToken);
//
//        HttpEntity<String> apiEntity = new HttpEntity<>(apiHeaders);
//
//// Make API requests with the bearer token
//        ResponseEntity<String> apiResponse = restTemplate.exchange("<your_api_url>", HttpMethod.POST, apiEntity, String.class);

// Handle the API response
// ...


    private Map<String, String> createRequestBody() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", "bewerber");
        requestBody.put("client_secret", "hj52Ws4kF");
        requestBody.put("grant_type", "client_credentials");
        return requestBody;

    }

}
