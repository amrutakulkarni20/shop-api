package com.shop.api.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseObject {
    private int currentPage;
    private int numberOfPages;
    private int numberOfResults;
    private List<Item> items;
}
