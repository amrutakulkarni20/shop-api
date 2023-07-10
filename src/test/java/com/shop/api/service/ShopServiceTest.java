package com.shop.api.service;

import com.shop.api.entity.CategoriesEntity;
import com.shop.api.entity.ShopEntity;
import com.shop.api.model.Category;
import com.shop.api.model.Item;
import com.shop.api.model.ResponseObject;
import com.shop.api.model.ShopModel;
import com.shop.api.repository.ShopRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceTest {

    @Value("${api.key}")
    private String apiKey;

    @Value("${shop.api.url}")
    private String apiUrl;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ShopApiClient shopApiClient;

    @Mock
    private ResponseEntity<String> responseEntity;

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    public void createShopsAndVerifiesId() {
        Category category1 = new Category("5f211b9460fa5c01f522ce94", "öbel & Deko");
        Category category2 = new Category("5f211b9460fa5c01f522ce77", "Haus & Garten");
        List<Item> items = Arrays.asList(new Item("5487813f6f9c4203288b4743", 1, "abc", Arrays.asList(category1, category2)));
        ResponseObject responseObject = new ResponseObject(1, 2, 3, items);
        when(shopApiClient.getAllShops()).thenReturn(responseObject);
        List<ShopEntity> shopsEntity = createShopResponse(ShopEntity.class);
        TypeToken<List<ShopEntity>> typeToken = new TypeToken<>() {};
        when(modelMapper.map(items, typeToken.getType())).thenReturn(shopsEntity);
        shopService.createShops();
        ArgumentCaptor<List<ShopEntity>> shopEntityArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(shopRepository, times(1)).saveAll(shopEntityArgumentCaptor.capture());
        List<ShopEntity> shopEntities = shopEntityArgumentCaptor.getValue();
        assertNotNull(shopEntities);
        assertEquals(shopEntities, shopsEntity);
        assertEquals(shopEntities.get(0).getId(), shopsEntity.get(0).getId());
    }

    @Test
    public void getAllShopsFromDatabaseAndVerifiesResponse(){
        List<ShopEntity> shopsEntityList = createShopResponse(ShopEntity.class);
        when(shopRepository.findAll()).thenReturn(shopsEntityList);
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
        when(modelMapper.map(shopsEntityList, typeToken.getType())).thenReturn(shopsModelList);
        List<ShopModel>  shops = shopService.getShops();
        assertNotNull(shops);
        assertEquals(shopsModelList, shops);
    }

    @Test
    public void test_getShopById_checksIdFromResponse(){
        final String id = "548446d66f9c421d61bb825d";
        List<ShopEntity> shopsEntityList = createShopResponse(ShopEntity.class);
        when(shopRepository.findById(id)).thenReturn(Optional.ofNullable(shopsEntityList.get(0)));
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        when(modelMapper.map(shopsEntityList.get(0), ShopModel.class)).thenReturn(shopsModelList.get(0));
        ShopModel shop = shopService.getShopById(id);
        assertNotNull(shop);
        assertEquals(shopsModelList.get(0), shop);
    }

    @Test
    public void test_updateShop_checkResponse(){
        final String id = "548446d66f9c421d61bb825d";
        List<ShopEntity> shopsEntityList = createShopResponse(ShopEntity.class);
        when(shopRepository.findById(id)).thenReturn(Optional.ofNullable(shopsEntityList.get(0)));
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        when(modelMapper.map(shopsEntityList.get(0), ShopModel.class)).thenReturn(shopsModelList.get(0));
        when(modelMapper.map(shopsModelList.get(0),ShopEntity.class)).thenReturn(shopsEntityList.get(0));
        shopService.updateShop(shopsModelList.get(0));
        ArgumentCaptor<ShopEntity> shopEntityArgumentCaptor = ArgumentCaptor.forClass(ShopEntity.class);
        verify(shopRepository, times(1)).save(shopEntityArgumentCaptor.capture());
        ShopEntity shopEntity = shopEntityArgumentCaptor.getValue();
        assertNotNull(shopEntity);
        assertEquals(shopEntity.getId(), shopsEntityList.get(0).getId());

    }

    @Test
    public void test_deleteShopById_checkResponse(){
        final String id = "548446d66f9c421d61bb825d";
        List<ShopEntity> shopsEntityList = createShopResponse(ShopEntity.class);
        when(shopRepository.findById(id)).thenReturn(Optional.ofNullable(shopsEntityList.get(0)));
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        when(modelMapper.map(shopsEntityList.get(0), ShopModel.class)).thenReturn(shopsModelList.get(0));
        shopService.deleteShopById(id);
        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(shopRepository, times(1)).deleteById(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());

    }

    private <T> List<T> createShopResponse(Class<T> clazz) {

        List<T> shopList = new ArrayList<>();

        if (clazz == ShopModel.class) {
            Category category1 = new Category("5f211b9460fa5c01f522ce94", "öbel & Deko");
            Category category2 = new Category("5f211b9460fa5c01f522ce77", "Haus & Garten");
            Category category3 = new Category("5f211b9460fa5c01f522ce94", "öbel & Deko");
            Category category4 = new Category("5f211b9460fa5c01f522ce77", "Haus & Garten");

            ShopModel shop1 = new ShopModel("548446d66f9c421d61bb825d", "Bob", "xyz", Arrays.asList(category1, category2));
            ShopModel shop2 = new ShopModel("5487813f6f9c4203288b4743", "John", "abc", Arrays.asList(category3, category4));

            shopList.add(clazz.cast(shop1));
            shopList.add(clazz.cast(shop2));
        } else if (clazz == ShopEntity.class) {
            CategoriesEntity category1 = new CategoriesEntity("5f211b9460fa5c01f522ce94", "öbel & Deko");
            CategoriesEntity category2 = new CategoriesEntity("5f211b9460fa5c01f522ce77", "Haus & Garten");
            CategoriesEntity category3 = new CategoriesEntity("5f211b9460fa5c01f522ce94", "öbel & Deko");
            CategoriesEntity category4 = new CategoriesEntity("5f211b9460fa5c01f522ce77", "Haus & Garten");

            ShopEntity shop1 = new ShopEntity("548446d66f9c421d61bb825d", "Bob", "xyz", Arrays.asList(category1, category2));
            ShopEntity shop2 = new ShopEntity("5487813f6f9c4203288b4743", "John", "abc", Arrays.asList(category3, category4));

            shopList.add(clazz.cast(shop1));
            shopList.add(clazz.cast(shop2));
        }
        return shopList;
    }

}
