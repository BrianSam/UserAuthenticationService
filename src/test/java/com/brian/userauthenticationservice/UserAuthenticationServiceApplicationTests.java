package com.brian.userauthenticationservice;

import com.brian.userauthenticationservice.config.authConfig.repositoriy.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
class UserAuthenticationServiceApplicationTests {

    @Autowired
    RegisteredClientRepository registeredClientRepository;
	@Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }
    @Test
    void test(){


         RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("brian")
                .clientSecret(passwordEncoder.encode("secret")) // 👈 bcrypt encoded
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope("openid")
                .scope("profile")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build())
                .build();


            registeredClientRepository.save(client);
    }

}
