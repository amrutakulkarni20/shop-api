package com.shop.api.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ErrorDetails {

    private ErrorCode code;
    private String message;
}
