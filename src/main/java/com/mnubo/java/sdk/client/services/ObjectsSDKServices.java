package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.validNotNull;

import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.spi.ObjectsSDK;

class ObjectsSDKServices implements ObjectsSDK {

    private final SDKService sdkCommonServices;

    ObjectsSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public void create(SmartObject object) {
        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OBJECT_PATH).build().toString();

        validNotNull(object, "Object body");
        notBlank(object.getObjectType(), "x_object_Type cannot be blank.");
        notBlank(object.getDeviceId(), "x_device_Id cannot be blank.");

        // posting
        sdkCommonServices. postRequest(url, SmartObject.class, object);

    }

    @Override
    public void update(SmartObject object, String deviceId) {
        notBlank(deviceId, "x_device_Id cannot be blank.");
        validNotNull(object, "Object body");

        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId).build().toString();

        // putting
        sdkCommonServices.putRequest(url, object);
    }

    @Override
    public void delete(String deviceId) {
        notBlank(deviceId, "x_device_Id cannot be blank.");

        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId).build().toString();

        // putting
        sdkCommonServices.deleteRequest(url);
    }


}
