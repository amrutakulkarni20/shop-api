package com.shop.api.controller;

import com.shop.api.ShopServiceApiApplication;
import com.shop.api.model.Category;
import com.shop.api.model.ShopModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
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

    @BeforeEach
    public void setup() {
        createShop();
    }

    @Test
    void createsShopsAndVerifiesStatusCode() {
        ResponseEntity<List<ShopModel>> createShopsApiResponse = createShop();
        assertNotNull(createShopsApiResponse);
        assertSame(HttpStatus.CREATED, createShopsApiResponse.getStatusCode());
    }

    private ResponseEntity<List<ShopModel>> createShop(){
        ResponseEntity<List<ShopModel>> shops = testRestTemplate.exchange(createURLForGetRequest("/shops", host, testPort),
                HttpMethod.POST, null, new ParameterizedTypeReference<>() {});
        return shops;
    }

    @Test
    void getsAllShopsAndVerifiesStatusCode() {
        ResponseEntity<List<ShopModel>> shops = testRestTemplate.exchange(createURLForGetRequest("/shopList", host, testPort),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        assertNotNull(shops);
        assertSame(HttpStatus.OK, shops.getStatusCode());
    }

    @Test
    void getsShopByIdAndVerifiesShopId() {
        final String id = "548446d66f9c421d61bb825d";
        ResponseEntity<ShopModel> shop = testRestTemplate.exchange(createURLForGetRequest("/shop/" + id, host, testPort),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        assertNotNull(shop);
        assertEquals(id, shop.getBody().getId());
    }

    @Test
    void updateShopAndVerifiesStatusCode() {
        ResponseEntity<ShopModel> updateShopRequest = createShopRequest();
        ResponseEntity<ShopModel> updateShopsApiResponse = testRestTemplate.exchange(createURLForGetRequest("/shop", host, testPort),
                HttpMethod.PUT, updateShopRequest, new ParameterizedTypeReference<>() {});
        assertNotNull(updateShopsApiResponse);
        assertSame(HttpStatus.CREATED, updateShopsApiResponse.getStatusCode());
    }

    @Test
    void deleteShopAndVerifiesStatus() {
        final String id = "548446d66f9c421d61bb825d";
        ResponseEntity<ShopModel> deleteShopResponse = testRestTemplate.exchange(createURLForGetRequest("/shop/" + id, host, testPort),
                HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {});
        assertNotNull(deleteShopResponse);
        assertSame(HttpStatus.OK, deleteShopResponse.getStatusCode());
    }

    private String createURLForGetRequest(final String uri, final String host,
                                                final String port) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append(uri);
        return stringBuilder.toString();
    }

    private ResponseEntity<ShopModel> createShopRequest() {
        Category category1 = new Category("5f211b9460fa5c01f522ce94", "öbel & Deko");
        Category category2 = new Category("5f211b9460fa5c01f522ce77", "Haus & Garten");
        ShopModel shopModel = new ShopModel("548446d66f9c421d61bb825d", "Bob", "xyz", Arrays.asList(category1, category2));
        return ResponseEntity.ok(shopModel);
    }
}
