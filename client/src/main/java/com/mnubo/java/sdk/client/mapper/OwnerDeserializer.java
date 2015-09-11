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

import static com.mnubo.java.sdk.client.models.Owner.PASSWORD;
import static com.mnubo.java.sdk.client.models.Owner.REGISTRATION_DATE;
import static com.mnubo.java.sdk.client.models.Owner.USERNAME;

import java.io.IOException;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mnubo.java.sdk.client.models.Owner;

public class OwnerDeserializer extends StdDeserializer<Owner> {
    private static final long serialVersionUID = 1L;

    public OwnerDeserializer() {
        super(Owner.class);
    }

    @Override
    public Owner deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        Owner.OwnerBuilder builder = Owner.builder();

        Map<String, Object> items = SDKMapperUtils.readValuesAsMap(jsonParser, String.class, Object.class);
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            if (entry.getKey().equals(USERNAME)) {
                builder.withUsername(entry.getValue().toString());
            }
            else if (entry.getKey().equals(PASSWORD)) {
                builder.withPassword(entry.getValue().toString());
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
