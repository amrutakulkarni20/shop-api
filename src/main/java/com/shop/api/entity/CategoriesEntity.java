package com.shop.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "category")
public class CategoriesEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
}
