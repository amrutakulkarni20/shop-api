package com.shop.api.service;

import com.shop.api.entity.ShopEntity;
import com.shop.api.model.ResponseObject;
import com.shop.api.model.ShopModel;
import com.shop.api.repository.ShopRepository;
import com.shop.api.security.TokenManager;
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

    private TokenManager tokenManager;

    private ShopApiClient shopApiClient;

    public ShopServiceImpl(ModelMapper modelMapper, ShopRepository shopRepository, TokenManager tokenManager,ShopApiClient shopApiClient) {
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
        this.tokenManager = tokenManager;
        this.shopApiClient = shopApiClient;
    }

    @Override
    public void createShops() {
        List<ShopEntity> shopFromApi = getShopsFromApi();
            shopRepository.saveAll(shopFromApi);
    }

    private List<ShopEntity> getShopsFromApi() {
        Optional<ResponseObject> shopFromApi = Optional.of(shopApiClient.getAllShops());
        TypeToken<List<ShopEntity>> typeToken = new TypeToken<>() {};
        List<ShopEntity> shopList = new ArrayList<>();
        if(shopFromApi.isPresent()){
            shopList =  modelMapper.map(shopFromApi.get().getItems(), typeToken.getType());
        }
        return shopList;
    }

    @Override
    public List<ShopModel> getShops() {
        List<ShopEntity> shopEntityList = shopRepository.findAll();
        TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
        return modelMapper.map(shopEntityList, typeToken.getType());
    }

    @Override
    public ShopModel getShop(String id) {
        Optional<ShopEntity> shopEntity = Optional.of(shopRepository.findById(id).orElseThrow());
        return modelMapper.map(shopEntity.get(),ShopModel.class);

    }
}
