/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Aug 4, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;

abstract class AbstractSDKService {

    private final RestTemplate template;
    private final CredentialHandler credential;
    private final MnuboSDKConfig config;

    AbstractSDKService(RestTemplate template, CredentialHandler credential, MnuboSDKConfig config) {
        this.credential = credential;
        this.template = template;
        this.config = config;
    }

    protected <T> T postRequest(String url, Class<T> objectClass, Object object) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(object, getAutorizationHeader());

        // send request
        return template.postForObject(url, request, objectClass);
    }

    protected HttpHeaders getAutorizationHeader() {
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", credential.getAutorizationToken());
        return headers;
    }

    protected MnuboSDKConfig getConfig() {
        return config;
    }

}
