package com.shop.api.security;

import com.shop.api.model.TokenRequestBody;
import com.shop.api.model.TokenResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name="accessTokenClient", url = "${base.url}" , configuration = AccessTokenInterceptor.class)
public interface AccessTokenClient {

    @PostMapping("/v2/iam/oauth/token")
    TokenResponseBody getAccessToken(@RequestBody TokenRequestBody tokenRequestBody);
}
