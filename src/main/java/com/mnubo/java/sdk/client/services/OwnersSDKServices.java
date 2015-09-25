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

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;
import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

class OwnersSDKServices extends AbstractSDKService implements OwnersSDK {

    private final String OWNER_PATH = "/owners";

    OwnersSDKServices(RestTemplate template, CredentialHandler credential, MnuboSDKConfig config) {
        super(template, credential, config);
    }

    @Override
    public void create(Owner owner) {
        // url
        final String url = UriComponentsBuilder.newInstance().host(getConfig().getHostName())
                .port(getConfig().getPlatformPort()).scheme(getConfig().getHttpProtocol())
                .path(getConfig().getHttpBasePath() + OWNER_PATH).build().toString();

        // posting
        postRequest(url, Owner.class, owner);

    }

    @Override
    public void claim(String username, String deviceId) {

        // url
        final String url = UriComponentsBuilder.newInstance().host(getConfig().getHostName())
                .port(getConfig().getPlatformPort()).scheme(getConfig().getHttpProtocol())
                .path(getConfig().getHttpBasePath() + OWNER_PATH + "/" + username + "/objects/" + deviceId + "/claim")
                .build().toString();

        // posting
        postRequest(url);

    }

}
