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

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${grant.type}")
    private String grantType;


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
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
        request.setGrant_type(grantType);
        return request;

    }

}
