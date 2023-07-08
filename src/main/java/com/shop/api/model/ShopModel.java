package com.shop.api.model;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopModel {

    private String id;

    private String name;

    private String description;

   private List<Category> categories;
}
