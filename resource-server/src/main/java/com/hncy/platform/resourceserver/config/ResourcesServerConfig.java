package com.hncy.platform.resourceserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import javax.sql.DataSource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.commons.io.IOUtils;
import java.io.IOException;

@EnableResourceServer
@Configuration
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;

    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(customAccessTokenConverter);

        Resource resource = new ClassPathResource("public.txt");
        String publicKey = null;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);

//        converter.setSigningKey(privateKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)throws Exception{
        resources.resourceId("user_api").tokenServices(tokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{


        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()

                .headers().addHeaderWriter((request, response) -> {
            response.addHeader("Access-Control-Allow-Origin", "*");

            if (request.getMethod().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }
}
