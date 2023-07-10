package com.shop.api.service;

import com.shop.api.model.Headers;
import com.shop.api.security.TokenManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShopApiInterceptor implements RequestInterceptor {
    @Value("${api.key}")
    private String apiKey;

    private TokenManager tokenManager;

    public ShopApiInterceptor(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(Headers.AUTHORIZATION.getValue(),"bearer "+tokenManager.getToken());
        requestTemplate.header(Headers.API_KEY.getValue(),apiKey);
    }

}
