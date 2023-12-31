package com.shop.api.service;

import com.shop.api.entity.CategoriesEntity;
import com.shop.api.entity.ShopEntity;
import com.shop.api.exception.InvalidInputDataException;
import com.shop.api.model.Category;
import com.shop.api.model.Item;
import com.shop.api.model.ResponseObject;
import com.shop.api.model.ShopModel;
import com.shop.api.repository.ShopRepository;
import com.shop.api.service.ShopApiClient;
import com.shop.api.service.ShopServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceImplTest {

    public ShopServiceImplTest(){}

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ShopApiClient shopApiClient;

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    public void createsShopsAndVerifiesId() {
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
    public void getsAllShopsFromDatabaseAndVerifiesResponse(){
        List<ShopEntity> shopsEntityList = createShopResponse(ShopEntity.class);
        Page<ShopEntity> mockShopPage = new PageImpl<>(shopsEntityList);
        when(shopRepository.findAll(any(Pageable.class))).thenReturn(mockShopPage);
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        TypeToken<List<ShopModel>> typeToken = new TypeToken<>() {};
        when(modelMapper.map(shopsEntityList, typeToken.getType())).thenReturn(shopsModelList);
        List<ShopModel> shops = shopService.getShops(PageRequest.of(0, 10));
        assertNotNull(shops);
        assertEquals(shopsModelList, shops);
    }

    @Test
    public void getsShopByIdAndVerifiesIdFromResponse(){
        final String id = "548446d66f9c421d61bb825d";
        List<ShopEntity> shopsEntityList = createShopResponse(ShopEntity.class);
        when(shopRepository.findById(id)).thenReturn(Optional.ofNullable(shopsEntityList.get(0)));
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        when(modelMapper.map(shopsEntityList.get(0), ShopModel.class)).thenReturn(shopsModelList.get(0));
        ShopModel shop = shopService.getShopById(id);
        assertNotNull(shop);
        assertEquals(shopsModelList.get(0), shop);
    }

    @Test(expected = InvalidInputDataException.class)
    public void throwsInvalidInputDataExceptionWhenInvalidShopIdPassedAndVerifiesResponse () throws InvalidInputDataException{
        final String id = "5484";
        when(shopRepository.findById(id)).thenThrow(InvalidInputDataException.class);
        shopService.getShopById(id);
    }

    @Test
    public void updatesShopSuccessfullyAndVerifiesResponse(){
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

    @Test(expected = InvalidInputDataException.class)
    public void throwsInvalidInputDataExceptionWhenInvalidShopIdPassedToUpdateShop() throws InvalidInputDataException{
        final String id = "5484";
        List<ShopModel> shopsModelList = createShopResponse(ShopModel.class);
        shopsModelList.get(0).setId(id);
        when(shopRepository.findById(id)).thenThrow(InvalidInputDataException.class);
        shopService.updateShop(shopsModelList.get(0));
    }

    @Test
    public void deletesShopSuccessfullyAndVerifiesResponse(){
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

    @Test(expected = InvalidInputDataException.class)
    public void throwsInvalidInputDataExceptionWhenInvalidShopIdPassedToDeleteShop() throws InvalidInputDataException{
        final String id = "5484";
        when(shopRepository.findById(id)).thenThrow(InvalidInputDataException.class);
        shopService.deleteShopById(id);
    }


    @Test
    public void getsAllShopsWhenShopsAreNotAvailable(){
        List<ShopEntity> shopsEntityList = new ArrayList<>();
        Page<ShopEntity> mockShopPage = new PageImpl<>(shopsEntityList);
        when(shopRepository.findAll(any(Pageable.class))).thenReturn(mockShopPage);
        TypeToken<Page<ShopModel>> typeToken = new TypeToken<>() {};
        List<ShopModel> shopsModelList = new ArrayList<>();
        List<ShopModel> shops = shopService.getShops(PageRequest.of(0, 10));
        assertTrue(shops.isEmpty());
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