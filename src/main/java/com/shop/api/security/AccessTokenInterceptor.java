package com.shop.api.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenInterceptor implements RequestInterceptor {

    @Value("${api.key}")
    private String apiKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(Headers.API_KEY.getValue(),apiKey);
    }


}
