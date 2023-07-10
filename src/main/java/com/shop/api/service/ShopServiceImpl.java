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
import java.util.List;
import java.util.Objects;
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
        List<ShopEntity> shopFromApi = getShopsFromApi();
            shopRepository.saveAll(shopFromApi);
    }

    private List<ShopEntity> getShopsFromApi() {
        Optional<ResponseObject> shopFromApi = Optional.of(shopApiClient.getAllShops());
        TypeToken<List<ShopEntity>> typeToken = new TypeToken<>() {};
        return modelMapper.map(shopFromApi.get().getItems(), typeToken.getType());
    }

    @Override
    public List<ShopModel> getShops() {
        List<ShopEntity> shopEntityList = shopRepository.findAll();
        TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
        return modelMapper.map(shopEntityList, typeToken.getType());
    }

    @Override
    public ShopModel getShop(String id) {
        Optional<ShopEntity> shopEntityOptional = shopRepository.findById(id);
        if (shopEntityOptional.isPresent()) {
            ShopEntity shopEntity = shopEntityOptional.get();
            return modelMapper.map(shopEntity, ShopModel.class);
        }
        throw new InvalidInputDataException("Shop not found with id: " + id);
    }

    @Override
    public void updateShop(ShopModel shopModel) {
        ShopModel shop = getShop(shopModel.getId());
        if(Objects.nonNull(shop)){
            ShopEntity shopEntity = modelMapper.map(shopModel,ShopEntity.class);
            shopRepository.save(shopEntity);
        }else{
            throw new InvalidInputDataException("Invalid input request");
        }
    }

    @Override
    public void deleteShop(String id) {
        ShopModel shop = getShop(id);
        if(Objects.nonNull(shop)){
            shopRepository.deleteById(id);
        }else{
            throw new InvalidInputDataException("Invalid input request");
        }
    }
}
