package com.shop.api.controller;

import com.shop.api.model.ShopModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Tag(name = "Shop API Service",description = "The Shop API Microservice exposes REST endpoints to add, update, delete and get the shop resource.")
public interface IShopController {

    @Operation(summary = "Creates new Shops", description = "This API creates new Shops.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Shops created successfully.")})
    void createShops();

    @Operation(summary = "Gets all Shops", description = "This API returns a list of Shops.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shops returned successfully.")})
    List<ShopModel> getShops();

    @Operation(summary = "Gets Shop by Id", description = "This API returns Shop by Shop Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop returned successfully.")})
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    ShopModel getShopById(@PathVariable("id") String id);

    @Operation(summary = "Update Shop by Id", description = "This API updates Shops.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop Updated successfully.")})
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    void updateShop(@Valid @RequestBody ShopModel shopModel);

    @Operation(summary = "Delete Shop by Id", description = "This API deletes Shop.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop Deleted successfully.")})
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    void deleteShopById(@PathVariable("id") String id);
}
