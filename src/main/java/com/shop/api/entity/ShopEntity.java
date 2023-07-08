package com.shop.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shop")
public class ShopEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",length = 1000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private List<CategoriesEntity> categories;
}
