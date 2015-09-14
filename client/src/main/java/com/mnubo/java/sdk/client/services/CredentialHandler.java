/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Aug 10, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.TOKEN_CONSUMER_SEPARATOR;
import static com.mnubo.java.sdk.client.Constants.TOKEN_GRANT_TYPE;
import static com.mnubo.java.sdk.client.Constants.TOKEN_GRANT_TYPE_VALUE;
import static com.mnubo.java.sdk.client.Constants.TOKEN_PATH;
import static com.mnubo.java.sdk.client.Constants.TOKEN_SCOPE;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.validIsBlank;
import static org.springframework.util.Base64Utils.encodeToString;

import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;

class CredentialHandler {
    private static long FLETCHING_TOKEN_MILISEC = 5000;
    private MnuboSDKConfig config;
    private Token credentials;
    private RestTemplate restTemplate;
    private DateTime expireTime;

    CredentialHandler(MnuboSDKConfig aConfig, RestTemplate template) {
        config = aConfig;
        restTemplate = template;
        requestToken();
    }

    private void setExpireTime(int seconds) {
        expireTime = DateTime.now().plusSeconds(seconds);
    }

    private void setCredentials(Token credential) {
        credentials = credential;
    }

    private void requestToken() {
        try {
            // url
            String url = UriComponentsBuilder.newInstance().host(config.getHostName())
                    .port(config.getAuthenticationPort()).scheme(config.getHttpProtocol()).path(TOKEN_PATH)
                    .queryParam(TOKEN_GRANT_TYPE, TOKEN_GRANT_TYPE_VALUE).queryParam(TOKEN_SCOPE, config.getScope())
                    .build().toString();

            // header
            HttpHeaders headers = new HttpHeaders();
            String autentication = config.getSecurityConsumerKey() + TOKEN_CONSUMER_SEPARATOR
                    + config.getSecurityConsumerSecret();
            headers.set("Authorization", "Basic " + encodeToString(autentication.getBytes()));

            // entity
            HttpEntity<String> request = new HttpEntity<String>(headers);

            // send request
            ResponseEntity<Token> response = restTemplate.exchange(url, HttpMethod.POST, request, Token.class);
            validIsBlank(response.getBody().getAccessToken(),
                    "Token not valid, check autentication server or credentials");
            validIsBlank(response.getBody().getTokenType(),
                    "Token not valid, check autentication server or credentials");
            setCredentials(response.getBody());
            setExpireTime(credentials.getExpiresIn());
        }
        catch (Exception ex) {
            setExpireTime(0);
            System.out.print(ex.getMessage());
            throw new IllegalStateException(ex.getCause() + ex.getMessage());
        }
    }

    String getAutorizationToken() {
        if (DateTime.now().getMillis() + FLETCHING_TOKEN_MILISEC > expireTime.getMillis()) {
            requestToken();
        }
        return credentials.getTokenType() + " " + credentials.getAccessToken();
    }

}
