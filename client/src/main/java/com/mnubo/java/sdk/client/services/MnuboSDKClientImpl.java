/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Jul 30, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.services;

import org.springframework.web.client.RestTemplate;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;
import com.mnubo.java.sdk.client.spi.EventsSDK;
import com.mnubo.java.sdk.client.spi.MnuboSDKClient;
import com.mnubo.java.sdk.client.spi.ObjectsSDK;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

final class MnuboSDKClientImpl implements MnuboSDKClient {
    private final ObjectsSDK objectCLient;
    private final OwnersSDK ownerCLient;
    private final EventsSDK eventCLient;

    MnuboSDKClientImpl(MnuboSDKConfig config) {
        // config file
        config.isInitialized();

        // configurating custom restTemplate
        RestTemplate restTemplate = new HttpRestTemplate(config).getRestTemplate();

        // credential handler
        CredentialHandler credentials = new CredentialHandler(config, restTemplate);

        // creating specific clients
        objectCLient = new ObjectsSDKServices(restTemplate, credentials, config);
        eventCLient = new EventsSDKServices(restTemplate, credentials, config);
        ownerCLient = new OwnersSDKServices(restTemplate, credentials, config);
    }

    @Override
    public ObjectsSDK getObjectClient() {
        return objectCLient;
    }

    @Override
    public EventsSDK getEventClient() {
        return eventCLient;
    }

    @Override
    public OwnersSDK getOwnerClient() {
        return ownerCLient;
    }

}
