package com.shop.api.security;

import com.shop.api.model.TokenRequestBody;
import com.shop.api.model.TokenResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class TokenManager {

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${grant.type}")
    private String grantType;

    @Autowired
    private AccessTokenClient accessTokenClient;

    private TokenInfo tokenInfo;

    public synchronized String getToken() {
        if (tokenInfo == null || isTokenExpired()) {
            refreshToken();
        }
        return tokenInfo.getToken();
    }

    private boolean isTokenExpired() {
        LocalDateTime expiresAt = tokenInfo.getExpiresAt();
        return expiresAt == null || LocalDateTime.now().isAfter(expiresAt);
    }

    private void refreshToken() {

        TokenResponseBody response = accessTokenClient.getAccessToken(createRequestBody());

        if (response != null) {
            String token = response.getAccessToken();
            long expiresIn = response.getExpiresIn();
            LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(expiresIn);
            tokenInfo = new TokenInfo(token, expiresAt);
        }
    }

    private TokenRequestBody createRequestBody() {
        TokenRequestBody request = new TokenRequestBody();
        request.setClientId(clientId);
        request.setClientSecret(clientSecret);
        request.setGrantType(grantType);
        return request;

    }

    private static class TokenInfo {
        private String token;
        private LocalDateTime expiresAt;

        public TokenInfo(String token, LocalDateTime expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }

        public String getToken() {
            return token;
        }

        public LocalDateTime getExpiresAt() {
            return expiresAt;
        }
    }
}
