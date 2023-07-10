package com.shop.api.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ErrorDetails {

    private String message;

    private String details;
}
