package com.shop.api.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenRequestBody {


    private String client_id;

    private String client_secret;

    private String grant_type;
}
