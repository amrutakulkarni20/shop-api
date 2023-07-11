package com.shop.api.service;

import com.shop.api.model.ShopModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService {

    void createShops();

    List<ShopModel> getShops(Pageable pageable);

    ShopModel getShopById(String id);

    void updateShop(ShopModel shopModel);

    void deleteShopById(String id);
}
