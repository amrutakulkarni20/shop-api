package com.shop.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.api.model.ShopModel;

import java.util.List;

public interface ShopService {

    List<ShopModel> createShops() throws JsonProcessingException;

    List<ShopModel> getShops();

    ShopModel getShop(String id);
}
