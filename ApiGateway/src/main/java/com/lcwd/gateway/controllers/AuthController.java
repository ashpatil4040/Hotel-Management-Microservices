package com.lcwd.gateway.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.gateway.models.AuthResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

      private Logger logger =  LoggerFactory.getLogger(AuthController.class);   

       @GetMapping("/login")
       public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
          @AuthenticationPrincipal OidcUser user, Model model
       ) {   
           logger.info("Inside login method of AuthController");
           logger.info("User id: {}", user.getEmail());

           //creating auth response 
           AuthResponse authResponse = new AuthResponse();
           //setting email to auth response
           authResponse.setUserId(user.getEmail());
           //setting token to auth response
           authResponse.setAccessToken(client.getAccessToken().getTokenValue());
           authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());
           authResponse.setExpiresAt(client.getAccessToken().getExpiresAt().getEpochSecond());

           //setting authorities to auth response
           List<String> authorites= user.getAuthorities().stream().map(
            grantedAuthority -> {
                    return grantedAuthority.getAuthority();
                }).collect(Collectors.toList());
           
           
           authResponse.setAuthorities( authorites);
        
           return new ResponseEntity<>(authResponse, HttpStatus.OK);
          
       }

}
