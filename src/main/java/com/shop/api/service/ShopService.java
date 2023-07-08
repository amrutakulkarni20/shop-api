package com.shop.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.api.model.ShopModel;

import java.util.List;

public interface ShopService {

    List<ShopModel> getShops() throws JsonProcessingException;

    List<ShopModel> getShopsFromDB();
}
