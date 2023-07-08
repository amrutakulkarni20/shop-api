package com.shop.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.api.entity.ShopEntity;
import com.shop.api.model.ResponseObject;
import com.shop.api.model.ShopModel;
import com.shop.api.repository.ShopRepository;
import com.shop.api.util.ApiClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${shop.api.url}")
    private String apiUrl;

    private ModelMapper modelMapper;

    private ShopRepository shopRepository;

    private ApiClient client;

    public ShopServiceImpl(ModelMapper modelMapper, ShopRepository shopRepository, ApiClient client) {
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
        this.client = client;
    }

    @Override
    public List<ShopModel> getShops() throws JsonProcessingException {
        String response = getShopList();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseObject responseObject = objectMapper.readValue(response, ResponseObject.class);
        TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
        List<ShopModel> shopObj = modelMapper.map(responseObject.getItems(), typeToken.getType());
        saveShops(shopObj);
        return shopObj;
    }

    private void saveShops(List<ShopModel> shopObj) {
        TypeToken<List<ShopEntity>> typeToken = new TypeToken<>() {};
        List<ShopEntity> shopList = modelMapper.map(shopObj, typeToken.getType());
        shopRepository.saveAll(shopList);
    }
    private String getShopList() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders apiHeaders = new HttpHeaders();
        apiHeaders.set("X-API-KEY", apiKey);
        apiHeaders.setBearerAuth(client.getAccountToken());
        HttpEntity<String> apiEntity = new HttpEntity<>(apiHeaders);
        ResponseEntity<String> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, apiEntity, String.class);
        return apiResponse.getBody();
    }

    @Override
    public List<ShopModel> getShopsFromDB() {
        List<ShopEntity> shopEntityList = shopRepository.findAll();
        TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
        List<ShopModel> shops = modelMapper.map(shopEntityList, typeToken.getType());
        return shops;
    }
}
