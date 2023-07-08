package com.shop.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.api.model.ShopModel;
import com.shop.api.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class ShopController {

    private ShopService shopService;

    public ShopController(ShopService shopService){
        this.shopService = shopService;
    }

    @GetMapping ("/shops")
    public List<ShopModel> getShops() throws JsonProcessingException {
        return shopService.getShops();
    }

    @GetMapping ("/shopList")
    public List<ShopModel> getShopsFromDB() throws JsonProcessingException {
        return shopService.getShopsFromDB();
    }
}
