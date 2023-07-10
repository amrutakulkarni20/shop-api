package com.shop.api.controller;

import com.shop.api.ShopServiceApiApplication;
import com.shop.api.model.Category;
import com.shop.api.model.ShopModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {ShopServiceApiApplication.class})
@TestPropertySource(locations = "classpath:test.properties")
class ShopControllerTest {

    private HttpHeaders httpHeaders;

    @Value("${server.port}")
    private String testPort;

    @Value("${application.test.host}")
    private String host;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void createsShopsAndVerifiesStatusCode() {

        ResponseEntity<List<ShopModel>> createShopsApiResponse = testRestTemplate.exchange(createURLForGetRequest("/shops", host, testPort),
                HttpMethod.POST, null, new ParameterizedTypeReference<>() {
                });
        assertNotNull(createShopsApiResponse);
        assertSame(HttpStatus.CREATED, createShopsApiResponse.getStatusCode());
    }

    @Test
    void getAllShopsAndVerifiesResponse() {
        ResponseEntity<List<ShopModel>> shops = getShop();
        assertNotNull(shops);
        assertSame(HttpStatus.OK, shops.getStatusCode());
    }

    private ResponseEntity<List<ShopModel>> getShop() {
        ResponseEntity<List<ShopModel>> shops = testRestTemplate.exchange(createURLForGetRequest("/shopList", host, testPort),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        return shops;
    }

    @Test
    void getShopsAndVerifiesShopId() {
        String id = "548446d66f9c421d61bb825d";
        ShopModel shop = getShopById(id);
        assertNotNull(shop);
        assertEquals(id, shop.getId());
    }

    private ShopModel getShopById(String id) {
        ResponseEntity<ShopModel> shop = testRestTemplate.exchange(createURLForGetRequest("/shop/" + id, host, testPort),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        return shop.getBody();
    }

    @Test
    void updateShopAndVerifiesResponse() {
        ResponseEntity<ShopModel> shopModel = createShopRequest();
        ResponseEntity<ShopModel> shop = updateShop(shopModel);
        assertNotNull(shop);
        assertSame(HttpStatus.CREATED, shop.getStatusCode());
    }

    private ResponseEntity<ShopModel> updateShop(ResponseEntity<ShopModel> shopModel) {
        ResponseEntity<List<ShopModel>> createShopsApiResponse = testRestTemplate.exchange(createURLForGetRequest("/shops", host, testPort),
                HttpMethod.POST, null, new ParameterizedTypeReference<>() {
                });

        ResponseEntity<ShopModel> updateShopsApiResponse = testRestTemplate.exchange(createURLForGetRequest("/shop", host, testPort),
                HttpMethod.PUT, shopModel, new ParameterizedTypeReference<>() {
                });
        return updateShopsApiResponse;
    }

    @Test
    void deleteShopAndVerifiesStatus() {
        ResponseEntity<ShopModel> shopModel = createShopRequest();
        ResponseEntity<List<ShopModel>> createShopsApiResponse = testRestTemplate.exchange(createURLForGetRequest("/shops", host, testPort),
                HttpMethod.POST, null, new ParameterizedTypeReference<>() {
                });
        ResponseEntity<ShopModel> shop = deleteShop(shopModel.getBody().getId());
        assertNotNull(shop);
        assertSame(HttpStatus.OK, shop.getStatusCode());
    }

    private ResponseEntity<ShopModel> deleteShop(String id) {
        ResponseEntity<ShopModel> shops = testRestTemplate.exchange(createURLForGetRequest("/shop/" + id, host, testPort),
                HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                });
        return shops;
    }

    public static String createURLForGetRequest(final String uri, final String host,
                                                final String port) {
        System.out.println(port);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append(uri);
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    private ResponseEntity<ShopModel> createShopRequest() {
        Category category1 = new Category("5f211b9460fa5c01f522ce94", "öbel & Deko");
        Category category2 = new Category("5f211b9460fa5c01f522ce77", "Haus & Garten");
        ShopModel shopModel = new ShopModel("548446d66f9c421d61bb825d", "Bob", "xyz", Arrays.asList(category1, category2));
        return ResponseEntity.ok(shopModel);
    }
}
