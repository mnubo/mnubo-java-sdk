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

import static com.mnubo.java.sdk.client.models.Owner.USERNAME;
import static com.mnubo.java.sdk.client.models.SmartObject.DEVICE_ID;
import static com.mnubo.java.sdk.client.models.SmartObject.OBJECT_TYPE;
import static com.mnubo.java.sdk.client.models.SmartObject.OWNER;
import static com.mnubo.java.sdk.client.models.SmartObject.REGISTRATION_DATE;

import java.io.IOException;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mnubo.java.sdk.client.models.SmartObject;

public class SmartObjectDeserializer extends StdDeserializer<SmartObject> {
    private static final long serialVersionUID = 1L;

    public SmartObjectDeserializer() {
        super(SmartObject.class);
    }

    @Override
    public SmartObject deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        SmartObject.SmartObjectBuilder builder = SmartObject.builder();

        Map<String, Object> items = SDKMapperUtils.readValuesAsMap(jsonParser, String.class, Object.class);
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            if (entry.getKey().equals(DEVICE_ID)) {
                builder.withDeviceId(entry.getValue().toString());
            }
            else if (entry.getKey().equals(OBJECT_TYPE)) {
                builder.withObjectType(entry.getValue().toString());
            }
            else if (entry.getKey().equals(OWNER)) {
                if (entry.getValue() instanceof Map<?, ?>) {
                    Map<?, ?> ownerMap = (Map<?, ?>) entry.getValue();
                    if (ownerMap.containsKey(USERNAME)) {
                        builder.withOwner(ownerMap.get(USERNAME).toString());
                    }
                }
            }
            else if (entry.getKey().equals(REGISTRATION_DATE)) {
                builder.withRegistrationDate(DateTime.parse(entry.getValue().toString()));
            }
            else {
                builder.withAddedAttribute(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();
    }

}
