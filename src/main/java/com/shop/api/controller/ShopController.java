package com.shop.api.controller;

import com.shop.api.model.ShopModel;
import com.shop.api.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {

    private ShopService shopService;

    public ShopController(ShopService shopService){
        this.shopService = shopService;
    }

    @PostMapping ("/shops")
    @ResponseStatus(HttpStatus.CREATED)
    public void createShops() {
         shopService.createShops();
    }

    @GetMapping ("/shopList")
    public List<ShopModel> getShops(){
        return shopService.getShops();
    }

    @GetMapping ("/shop/{id}")
    public ShopModel getShop(@PathVariable("id") String id){
        return shopService.getShop(id);
    }

    //update

    //delete
}
