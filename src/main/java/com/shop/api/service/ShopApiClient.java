package com.shop.api.service;

import com.shop.api.model.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name="shopApiClient", url = "${base.url}" , configuration = ShopApiInterceptor.class)
public interface ShopApiClient {

    @GetMapping("/v3/cashback/shops")
    ResponseObject getAllShops();
}
