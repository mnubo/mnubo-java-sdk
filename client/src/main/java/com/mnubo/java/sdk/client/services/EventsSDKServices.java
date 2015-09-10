/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Jul 24, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;
import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.spi.EventsSDK;

class EventsSDKServices extends AbstractSDKService implements EventsSDK {

    private final String EVENT_PATH = "/events";

    EventsSDKServices(RestTemplate template, CredentialHandler credential, MnuboSDKConfig config) {
        super(template, credential, config);
    }

    @Override
    public void send(String objectId, List<Event> events) {
        // url
        final String url = UriComponentsBuilder.newInstance().host(getConfig().getHostName())
                .port(getConfig().getPlatformPort()).scheme(getConfig().getHttpProtocol())
                .path(getConfig().getHttpBasePath() + OBJECT_PATH + "/" + objectId + EVENT_PATH).build().toString();

        // posting
        postRequest(url, Event.class, events);
    }

    @Override
    public void send(List<Event> events) {
        for (Event event : events) {
            List<Event> eventsByObjectId = new ArrayList<Event>();
            eventsByObjectId.add(event);
            final String url = UriComponentsBuilder.newInstance().host(getConfig().getHostName())
                    .port(getConfig().getPlatformPort()).scheme(getConfig().getHttpProtocol())
                    .path(getConfig().getHttpBasePath() + EVENT_PATH).build().toString();
            // posting
            postRequest(url, Event.class, eventsByObjectId);
        }
    }
}
