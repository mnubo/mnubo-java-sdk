package com.mnubo.java.sdk.client.services;

import org.springframework.web.client.RestTemplate;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;
import com.mnubo.java.sdk.client.spi.EventsSDK;
import com.mnubo.java.sdk.client.spi.MnuboSDKClient;
import com.mnubo.java.sdk.client.spi.ObjectsSDK;
import com.mnubo.java.sdk.client.spi.OwnersSDK;
import com.mnubo.java.sdk.client.spi.SearchSDK;

final class MnuboSDKClientImpl implements MnuboSDKClient {
    private final ObjectsSDK objectCLient;
    private final OwnersSDK ownerCLient;
    private final EventsSDK eventCLient;
    private final SearchSDK searchCLient;
    private final SDKService sdkService;

    MnuboSDKClientImpl(MnuboSDKConfig config, RestTemplate restTemplate, CredentialHandler credentials) {

        // creating SDK service instance
        sdkService = new SDKService(restTemplate, credentials, config);

        // creating specific clients
        objectCLient = new ObjectsSDKServices(sdkService);
        eventCLient = new EventsSDKServices(sdkService);
        ownerCLient = new OwnersSDKServices(sdkService);
        searchCLient = new SearchSDKServices(sdkService);
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

    @Override
    public SearchSDK getSearchClient() {
        return searchCLient;
    }

    public SDKService getSdkService() {
        return sdkService;
    }
}
