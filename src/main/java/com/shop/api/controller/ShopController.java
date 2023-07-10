package com.shop.api.controller;

import com.shop.api.model.ShopModel;
import com.shop.api.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping ("/shops")
    public List<ShopModel> getShops(){
        return shopService.getShops();
    }

    @GetMapping ("/shop/{id}")
    public ShopModel getShopById(@PathVariable("id") String id){
        return shopService.getShopById(id);
    }

    @PutMapping ("/shop")
    public void updateShop(@Valid @RequestBody ShopModel shopModel){
        shopService.updateShop(shopModel);
    }

    @DeleteMapping ("/shop/{id}")
    public void deleteShopById(@PathVariable("id") String id){
         shopService.deleteShopById(id);
    }
}
