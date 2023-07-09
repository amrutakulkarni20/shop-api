package com.shop.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.api.model.ShopModel;
import com.shop.api.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class ShopController {

    private ShopService shopService;

    public ShopController(ShopService shopService){
        this.shopService = shopService;
    }

    @PostMapping ("/shops")
    public List<ShopModel> createShops() throws JsonProcessingException {
        return shopService.createShops();
    }

    @GetMapping ("/shopList")
    public List<ShopModel> getShops(){
        return shopService.getShops();
    }

    @GetMapping ("/shop/{id}")
    public ShopModel getShop(@PathVariable("id") String id){
        return shopService.getShop(id);
    }
}
