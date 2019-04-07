package com.hncy.platform.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import javax.sql.DataSource;
import com.hncy.platform.authorizationserver.service.UserService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import java.util.Arrays;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private String privateKey = "private-key";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StandardPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("auth.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("auth"));
//        converter.setSigningKey(privateKey);

        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), tokenConverter()));

        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        clients.withClientDetails(jdbcClientDetailsService);
    }

}
