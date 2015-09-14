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

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnubo.java.sdk.client.config.MnuboSDKConfig;
import com.mnubo.java.sdk.client.mapper.SDKObjectMapperConfig;

class HttpRestTemplate {
    private RestTemplate restTemplate;

    HttpRestTemplate(MnuboSDKConfig config) {
        HttpClient httpClient = getHttpClient(config);
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate = new RestTemplate(requestFactory);
        configureMapper(SDKObjectMapperConfig.getObjectMapper());
    }

    private void configureMapper(ObjectMapper objectMapper) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }

    private HttpClient getHttpClient(MnuboSDKConfig config) {
        // Setting request config
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(config.getHttpDefaultTimeout())
                .setConnectionRequestTimeout(config.getHttpConnectionRequestTimeout())
                .setSocketTimeout(config.getHttpSoketTimeout()).build();

        // Setting pooling management
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(config.getHttpMaxTotalConnection());
        connectionManager.setDefaultMaxPerRoute(config.getHttpMaxConnectionPerRoute());
        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(config.getHostName())),
                config.getHttpMaxConnectionPerRoute());
        connectionManager.setValidateAfterInactivity(config.getHttpDefaultTimeout());

        // Building httpclient
        HttpClientBuilder httpClientsBuilder = HttpClients.custom().setDefaultRequestConfig(requestConfig);
        if (config.isHttpDisableCockieManagement()) {
            httpClientsBuilder.disableCookieManagement();
        }
        if (config.isHttpDisableRedirectHandling()) {
            httpClientsBuilder.disableRedirectHandling();
        }
        if (config.isHttpDisableAutomaticRetries()) {
            httpClientsBuilder.disableAutomaticRetries();
        }
        if (config.isHttpSystemPropertiesEnable()) {
            httpClientsBuilder.useSystemProperties();
        }
        httpClientsBuilder.setMaxConnPerRoute(config.getHttpMaxConnectionPerRoute())
                .setMaxConnTotal(config.getHttpMaxConnectionPerRoute() * 2).setConnectionManager(connectionManager);

        return httpClientsBuilder.build();
    }

    RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
