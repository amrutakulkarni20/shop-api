package com.shop.api.service;

import com.shop.api.model.ShopModel;
import java.util.List;

public interface ShopService {

    void createShops();

    List<ShopModel> getShops();

    ShopModel getShop(String id);

    void updateShop(ShopModel shopModel);

    void deleteShop(String id);
}
