package com.mnubo.java.sdk.client.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;

class SDKService {

    private RestTemplate template;
    private final CredentialHandler credential;
    private final MnuboSDKConfig config;

    SDKService(RestTemplate template, CredentialHandler credential, MnuboSDKConfig config) {
        this.credential = credential;
        this.template = template;
        this.config = config;
    }

    void postRequest(String url) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(getAutorizationHeader());

        template.postForEntity(url, request, String.class);
    }

    <T> T postRequest(String url, Class<T> objectClass, Object object) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(object, getAutorizationHeader());

        // send request
        return template.postForObject(url, request, objectClass);
    }

    void putRequest(String url, Object object) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(object, getAutorizationHeader());

        template.put(url, request);
    }

    <T> ResponseEntity<T> getRequestResponseEntity(String url, Class<T> objectClass) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(getAutorizationHeader());
       
        return template.exchange(url, HttpMethod.GET, request, objectClass);
    }

    <T> T getRequest(String url, Class<T> objectClass) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(getAutorizationHeader());
        ResponseEntity<T> response = template.exchange(url, HttpMethod.GET, request, objectClass);
        if(response == null) {
            return null;
        }
        else {
            return response.getBody();
        }
    }

    void deleteRequest(String url) {
        // entity
        HttpEntity<?> request = new HttpEntity<Object>(getAutorizationHeader());

        template.exchange(url, HttpMethod.DELETE, request, String.class);
    }

    HttpHeaders getAutorizationHeader() {
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", credential.getAutorizationToken());
        return headers;
    }

    MnuboSDKConfig getConfig() {
        return config;
    }

    UriComponentsBuilder getIngestionBaseUri() {
        return UriComponentsBuilder.newInstance().host(getConfig().getHostName())
                                   .port(getConfig().getPlatformPort()).scheme(getConfig().getHttpProtocol())
                                   .path(getConfig().getHttpBasePath());
    }
    
    UriComponentsBuilder getRestitutionBaseUri() {
        return UriComponentsBuilder.newInstance().host(getConfig().getHostName())
                                   .port(getConfig().getRestitutionPort()).scheme(getConfig().getHttpProtocol())
                                   .path(getConfig().getHttpBasePath());
    }

}
