/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Jul 16, 2015
 *
 * ---------------------------------------------------------------------------
 */

package client;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.services.MnuboSDKFactory;
import com.mnubo.java.sdk.client.spi.MnuboSDKClient;

public class SDKUsageExample {
    private static String HOST = "here the host, example 'rest.sandbox.mnubo.com'";
    private static String CONSUMER_KEY = "here you consumer key";
    private static String CONSUMER_SECRET = "here you consumer secret";

    private MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient(HOST, CONSUMER_KEY, CONSUMER_SECRET);

    public SDKUsageExample() throws Exception {
    }

    /**
     *
     * This is an example to create a new object or update it if already exists.
     *
     * @param object2Create, Object to be created.
     */
    public void createObject(SmartObject object2Create) {
        // Posting object SDK call
        mnuboClient.getObjectClient().create(object2Create);
    }

    /**
     *
     * This is an example to post event to ONE object.
     *
     * @param objectId, Object to be posted,
     * @param events, events to be posted in the object. Please check resource
     * eventvalues.json file to get an example about the this object.
     */
    public void postEvents2Object(String objectId, List<Event> events) {
        // Posting events to only one object
        mnuboClient.getEventClient().send(objectId, events);
    }

    /**
     *
     * This is an example to post event to ONE or SEVERAL objects.
     *
     * @param events, events to be posted in objects. Please check resource event.json
     * file to get an example about the this object.
     */
    public void postEvents2Object(List<Event> events) {
        // Posting events to only one object
        mnuboClient.getEventClient().send(events);
    }

    /**
     *
     * This is an example to create a new owner.
     *
     * @param owner2Create, Owner to be created.
     */
    public void createOwner(Owner owner2Create) {
        // Posting events to only one object
        mnuboClient.getOwnerClient().create(owner2Create);
    }

    /**
     *
     * This is an example how to build a new owner. Please note that only "username" is a
     * mandatory field, others ones are optional.
     *
     */
    public Owner buildingOwner() {
        //@formatter:off
        return Owner.builder()
                .withUsername(/* username, this is MANDATORY */"John@mnubotest.com")
                .withPassword(/* password */"PassWORD!??%3")
                .withRegistrationDate(/* datetime of registration */DateTime.parse("2014-01-27T08:01:01.000Z"))
                .withAttributes(/* add here all attributes as a MAP */new HashMap<String, Object>())
                .withAddedAttribute("Here the attribute key", "here its value as \"Object\"")
                .withAddedAttribute("age", 35)
                .withAddedAttribute("contry", "USA")
                .build();
        //@formatter:on
    }

    /**
     *
     * This is an example how to build a new SmartObject. Please note that "deviceId" and
     * "ObjectType" are mandatory fields, others ones are optional.
     *
     */
    public SmartObject buildingSmartObject() {
        //@formatter:off
        return SmartObject.builder()
                .withDeviceId(/* deviceId, this is MANDATORY */"Room_temperature_02154")
                .withObjectType(/* object type, this is MANDATORY */"Thermostat")
                .withOwner(/* owner's username */"John@mnubotest.com")
                .withRegistrationDate(/* datetime of registration */DateTime.now())
                .withAttributes(/* add here all attributes as a MAP */new HashMap<String, Object>())
                .withAddedAttribute("Here the attribute key", "here its value as \"Object\"")
                .withAddedAttribute("contry", "Sydney")
                .withAddedAttribute("address", "42 Wallaby Way")
                .withAddedAttribute("film", "nemo")
                .build();
        //@formatter:on
    }

    /**
     *
     * This is an example how to build a new Event. Please note that "deviceId" and
     * "ObjectType" are mandatory fields, others ones are optional.
     *
     */
    public Event buildingEvent() {
        //@formatter:off
       return Event.builder()
               .withEventType(/* eventType, this is MANDATORY */"temperature")
               .withEventID(/* event id */ UUID.fromString("11111111-2222-3333-4444-555555555555"))
               .withSmartObject(/* object's deviceId */"Room_temperature_02154")
               .withTimestamp(/* datetime of posting */DateTime.now())
               .withTimeseries(/* add here all attributes as a MAP */new HashMap<String, Object>())
               .withAddedTimeseries("Here the attribute key", "here its value as \"Object\"")
               .withAddedTimeseries("temperature", 75)
               .withAddedTimeseries("id", "0238510.5654.56")
               .build();
       //@formatter:on
    }
}
