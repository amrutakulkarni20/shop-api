package com.shop.api.service;

import com.shop.api.entity.ShopEntity;
import com.shop.api.exception.InvalidInputDataException;
import com.shop.api.model.ResponseObject;
import com.shop.api.model.ShopModel;
import com.shop.api.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${shop.api.url}")
    private String shopApiUrl;

    private ModelMapper modelMapper;

    private ShopRepository shopRepository;

    private ShopApiClient shopApiClient;

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
        Optional<List<ShopEntity>> shops = Optional.of(shopRepository.findAll());
        if (shops.isPresent()) {
            TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
            return modelMapper.map(shops.get(), typeToken.getType());
        }
        return new ArrayList<ShopModel>();
    }

    @Override
    public ShopModel getShopById(String id) {
        Optional<ShopEntity> shop = shopRepository.findById(id);
        shop.orElseThrow(()-> new InvalidInputDataException("Shop not found with id: " +id));
        return modelMapper.map(shop.get(), ShopModel.class);
    }

    @Override
    public void updateShop(ShopModel shopModel) {
        Optional<ShopModel> shop = Optional.of(getShopById(shopModel.getId()));
        shop.orElseThrow(()-> new InvalidInputDataException("Shop not found with id: " +shopModel.getId()));
        ShopEntity shopEntity = modelMapper.map(shopModel, ShopEntity.class);
        shopRepository.save(shopEntity);
    }

    @Override
    public void deleteShopById(String id) {
        Optional<ShopModel> shop = Optional.of(getShopById(id));
        shop.orElseThrow(()-> new InvalidInputDataException("Shop not found with id: " +id));
        shopRepository.deleteById(id);
    }
}
