package com.hncy.service.client.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hncy.service.client.domain.User;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
@Component
public class UserController {

    @Autowired
    private OAuth2ProtectedResourceDetails resource;

    @GetMapping("/users")
    @HystrixCommand(fallbackMethod = "findDefaultAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAll(Principal principal) {
        OAuth2RestTemplate restTemplate = restTemplate(principal);
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8082/user/users", User[].class);

        return Arrays.asList(response.getBody());
    }

    public List<User> findDefaultAll() {
        return new ArrayList<User>();
    }

    private OAuth2RestTemplate restTemplate(Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        details.getTokenValue();
        OAuth2ClientContext context = new DefaultOAuth2ClientContext(new DefaultOAuth2AccessToken(details.getTokenValue()));
        return new OAuth2RestTemplate(resource, context);
    }

}
