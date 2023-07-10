package com.shop.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenResponseBody {

    private String access_token;

    private long expires_in;

    private String token_type;

    private String [] scope;

}
