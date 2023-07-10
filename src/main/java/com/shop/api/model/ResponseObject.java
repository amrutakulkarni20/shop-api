package com.shop.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
