package com.shop.api.security;
import com.shop.api.model.TokenResponseBody;
import com.shop.api.util.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class TokenManager {

    @Autowired
    private ApiClient apiClient;

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
            TokenResponseBody response = apiClient.getAccountToken();

            if (response != null) {
                String token = response.getAccess_token();
                long expiresAt = response.getExpires_in();
                LocalDateTime expiresAt1 = LocalDateTime.now().plusSeconds(expiresAt);
                tokenInfo = new TokenInfo(token, expiresAt1);
            }
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
