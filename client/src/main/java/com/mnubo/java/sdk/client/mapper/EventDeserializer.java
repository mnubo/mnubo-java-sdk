/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Mnubo Inc. The program(s) may be used and/or copied
 * only with the written permission from Mnubo Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias
 * Date  : Sep 9, 2015
 *
 * ---------------------------------------------------------------------------
 */
package com.mnubo.java.sdk.client.mapper;

import static com.mnubo.java.sdk.client.models.Event.EVENT_ID;
import static com.mnubo.java.sdk.client.models.Event.EVENT_TYPE;
import static com.mnubo.java.sdk.client.models.Event.OBJECT;
import static com.mnubo.java.sdk.client.models.Event.TIMESTAMP;
import static com.mnubo.java.sdk.client.models.SmartObject.DEVICE_ID;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mnubo.java.sdk.client.models.Event;

public class EventDeserializer extends StdDeserializer<Event> {
    private static final long serialVersionUID = 1L;

    public EventDeserializer() {
        super(Event.class);
    }

    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        Event.EventBuilder builder = Event.builder();

        Map<String, Object> items = SDKMapperUtils.readValuesAsMap(jsonParser, String.class, Object.class);
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            if (entry.getKey().equals(EVENT_ID)) {
                builder.withEventID((UUID) entry.getValue());
            }
            else if (entry.getKey().equals(EVENT_TYPE)) {
                builder.withEventType(entry.getValue().toString());
            }
            else if (entry.getKey().equals(OBJECT)) {
                if (entry.getValue() instanceof Map<?, ?>) {
                    Map<?, ?> objectMap = (Map<?, ?>) entry.getValue();
                    if (objectMap.containsKey(DEVICE_ID)) {
                        builder.withSmartObject(objectMap.get(DEVICE_ID).toString());
                    }
                }
            }
            else if (entry.getKey().equals(TIMESTAMP)) {
                builder.withTimestamp(DateTime.parse(entry.getValue().toString()));
            }
            else {
                builder.withAddedTimeseries(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();
    }

}
