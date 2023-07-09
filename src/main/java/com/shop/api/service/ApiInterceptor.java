package com.shop.api.service;

import com.shop.api.security.TokenManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiInterceptor implements RequestInterceptor {

    @Value("${api.key}")
    private String apiKey;

    private TokenManager tokenManager;

    public ApiInterceptor(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization","bearer "+tokenManager.getToken());
        requestTemplate.header("X-API-KEY",apiKey);
    }
}
