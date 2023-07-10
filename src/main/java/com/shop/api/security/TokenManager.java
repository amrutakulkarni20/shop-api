package com.shop.api.security;
import com.shop.api.model.TokenRequestBody;
import com.shop.api.model.TokenResponseBody;
import com.shop.api.service.AccessTokenClient;
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
            // Call your RestTemplate to obtain the new token
            // Assuming the response includes the new token and its expiration time
            TokenResponseBody response = accessTokenClient.getAccessToken(createRequestBody());

            if (response != null) {
                String token = response.getAccess_token();
                long expiresAt = response.getExpires_in();
                LocalDateTime expiresAt1 = LocalDateTime.now().plusSeconds(expiresAt);
                tokenInfo = new TokenInfo(token, expiresAt1);
            }
        }

    private TokenRequestBody createRequestBody() {
        TokenRequestBody request = new TokenRequestBody();
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
        request.setGrant_type(grantType);
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
