package com.shop.api.model;

import java.util.List;

public class ShopModel {

    public ShopModel(){}

    private String id;

    private String name;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public ShopModel(String id, String name, String description, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    private List<Category> categories;
}
