package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notNullNorEmpty;

import java.util.List;

import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.spi.EventsSDK;

class EventsSDKServices implements EventsSDK {

    public static final String EVENT_PATH = "/events";
    public static final String EVENT_PATH_SEGMENT = "events";
    private final SDKService sdkCommonServices;

    EventsSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public void send(String deviceId, List<Event> events) {

        notBlank(deviceId, "x_device_Id cannot be blank.");
        notNullNorEmpty(events, "Event list cannot be null or empty.");

        // url
        final String url = sdkCommonServices.getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId, EVENT_PATH_SEGMENT)
                .build().toString();

        // posting
        sdkCommonServices.postRequest(url, Event.class, events);
    }

    @Override
    public void send(List<Event> events) {

        notNullNorEmpty(events, "Event list cannot be null or empty.");

    	final String url = sdkCommonServices.getIngestionBaseUri().path(EVENT_PATH).build().toString();
    	sdkCommonServices.postRequest(url, Event.class, events);
    }
}
