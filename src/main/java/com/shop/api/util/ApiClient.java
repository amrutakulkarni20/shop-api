package com.shop.api.util;

import com.shop.api.model.TokenRequestBody;
import com.shop.api.model.TokenResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ApiClient
{
    @Value("${api.key}")
    private String apiKey;

    @Value("${token.api.url}")
    private String apiUrl;


    public TokenResponseBody getAccountToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        TokenRequestBody requestBody = createRequestBody();
        HttpEntity<TokenRequestBody> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<TokenResponseBody> response = restTemplate.postForEntity(apiUrl, entity, TokenResponseBody.class);
        if (response.getBody() != null && response.getBody().getAccess_token() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Access token not found");
        }
    }

    private TokenRequestBody createRequestBody() {
        TokenRequestBody request = new TokenRequestBody();
        request.setClient_id("bewerber");
        request.setClient_secret("hj52Ws4kF");
        request.setGrant_type("client_credentials");
        return request;

    }

}
