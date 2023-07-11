package com.shop.api.service;

import com.shop.api.entity.ShopEntity;
import com.shop.api.exception.InvalidInputDataException;
import com.shop.api.model.ResponseObject;
import com.shop.api.model.ShopModel;
import com.shop.api.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private ModelMapper modelMapper;

    private ShopRepository shopRepository;

    private ShopApiClient shopApiClient;

    private static final String SHOP_NOT_FOUND_MESSAGE = "Shop not found with id: ";

    public ShopServiceImpl(ModelMapper modelMapper, ShopRepository shopRepository, ShopApiClient shopApiClient) {
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
        this.shopApiClient = shopApiClient;
    }

    @Override
    public void createShops() {
        Optional<ResponseObject> shopsFromApi = Optional.of(shopApiClient.getAllShops());
        shopsFromApi.ifPresent(shops-> {
            TypeToken<List<ShopEntity>> typeToken = new TypeToken<>() {};
            final List<ShopEntity> shopsToSave = modelMapper.map(shops.getItems(), typeToken.getType());
            shopRepository.saveAll(shopsToSave);
        });
    }

    @Override
    public List<ShopModel> getShops() {
        List<ShopEntity> shops = shopRepository.findAll();
        if (!shops.isEmpty()) {
            TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
            return modelMapper.map(shops, typeToken.getType());
        }
        return new ArrayList<>();
    }

    @Override
    public ShopModel getShopById(String id) {
        Optional<ShopEntity> shop = shopRepository.findById(id);
        if(shop.isPresent()){
            return modelMapper.map(shop.get(), ShopModel.class);
        } else {
            throw new InvalidInputDataException(SHOP_NOT_FOUND_MESSAGE +id);
        }
    }

    @Override
    public void updateShop(ShopModel shopModel) {
        getShopById(shopModel.getId());
        ShopEntity shopEntity = modelMapper.map(shopModel, ShopEntity.class);
        shopRepository.save(shopEntity);
    }

    @Override
    public void deleteShopById(String id) {
        ShopModel shop = getShopById(id);
        shopRepository.deleteById(shop.getId());
    }
}
