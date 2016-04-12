package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.validNotNull;

import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

class OwnersSDKServices implements OwnersSDK {

    public static final String OWNER_PATH = "/owners";
    private final SDKService sdkCommonServices;

    OwnersSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public void create(Owner owner) {
        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OWNER_PATH).build().toString();

        validNotNull(owner, "Owner body");
        notBlank(owner.getUsername(), "usermame cannot be blank.");

        // posting
        sdkCommonServices.postRequest(url, Owner.class, owner);
    }

    @Override
    public void claim(String username, String deviceId) {
        notBlank(username, "usermame cannot be blank.");
        notBlank(deviceId, "x_deviceId cannot be blank.");

        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OWNER_PATH).pathSegment(username, "objects", deviceId, "claim")
                                                         .build().toString();
        // posting
        sdkCommonServices.postRequest(url);
    }

    @Override
    public void update(Owner owner, String username) {
        notBlank(username, "usermame cannot be blank.");

        validNotNull(owner, "Owner body");

        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OWNER_PATH).pathSegment(username).build().toString();

        // putting
        sdkCommonServices.putRequest(url, owner);
    }

    @Override
    public void delete(String username) {
        notBlank(username, "usermame cannot be blank.");

        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OWNER_PATH).pathSegment(username).build().toString();

        // putting
        sdkCommonServices.deleteRequest(url);
    }

}
