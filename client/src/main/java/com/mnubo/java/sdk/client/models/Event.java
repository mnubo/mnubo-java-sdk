/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Aug 10, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.utils.ValidationUtils.validIsBlank;
import static org.joda.time.DateTime.now;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * Event Bean. To build an event you must ask for EventBuilder using: the static method
 * event.builder()
 *
 * @author Mauro Arias
 * @since 2015/08/10
 */
public class Event {

    /**
     * {@value #EVENT_ID} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String EVENT_ID = "event_id";

    /**
     * {@value #OBJECT} Constant key used during the deserialization and serialization of
     * json files.
     */
    public static final String OBJECT = "x_object";

    /**
     * {@value #EVENT_TYPE} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String EVENT_TYPE = "x_event_type";

    /**
     * {@value #TIMESTAMP} Constant key used during the deserialization and serialization
     * of json files.
     */
    public static final String TIMESTAMP = "x_timestamp";

    private UUID eventId;
    private SmartObject smartObject;
    private String eventType;
    private DateTime timestamp = now();
    private Map<String, Object> timeseries = new HashMap<String, Object>();

    Event(UUID eventId, SmartObject smartObject, String eventType, DateTime timestamp, Map<String, Object> timeseries) {
        validIsBlank(eventType, "X_Event_Type cannot be null or empty");
        this.eventId = eventId;
        this.smartObject = smartObject;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.timeseries = timeseries;
    }

    /**
     * Builder, returns a EventBuilder to build immutable event instances.
     *
     * @return EventBuilder Builder.
     */
    public static EventBuilder builder() {
        return new EventBuilder();
    }

    /**
     * EventBuilder nested class, this static Class builds immutable events. this needs to
     * be requested with "Event.builder() method".
     *
     */
    public static class EventBuilder {

        private UUID eventId;
        private SmartObject smartObject;
        private String eventType;
        private DateTime timestamp = now();
        private Map<String, Object> timeseries = new HashMap<String, Object>();

        EventBuilder() {

        }

        /**
         * Add an eventId to the event.
         *
         * @param eventId: uuid event id
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withEventID(UUID eventId) {
            this.eventId = eventId;
            return this;
        }

        /**
         * Add a device Id to the event to be posted.
         *
         * @param deviceId: object device id
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withSmartObject(String deviceId) {
            this.smartObject = SmartObject.builder().withDeviceId(deviceId).withObjectType("send event").build();
            return this;
        }

        /**
         * Add an eventType to the event. This parameter is mandatory.
         *
         * @param eventType: event type
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withEventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        /**
         * Add a timestamp, date to the event. if this parameter is not set the current
         * time is set by default.
         *
         * @param timestamp: DateTime of the post.
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withTimestamp(DateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Add a list of timeseries to the event.
         *
         * @param timeseries: Map of timeseries.
         * @return EventBuilder: current event builder.
         *
         */
        public EventBuilder withTimeseries(Map<String, Object> timeseries) {
            this.timeseries = timeseries;
            return this;
        }

        /**
         * Add an timeseries to the event.
         *
         * @param key: timeseries key or name.
         * @param value: timeseries value.
         * @return SmartObjectBuilder: current Smart Object builder.
         *
         */

        public EventBuilder withAddedTimeseries(String key, Object value) {
            this.timeseries.put(key, value);
            return this;
        }

        /**
         * Build the immutable event with parameters set. Note that Event_type parameter
         * is mandatory.
         * @return Event: immutable event instance built.
         */
        public Event build() {
            return new Event(eventId, smartObject, eventType, timestamp, timeseries);
        }

    }

    /**
     * returns timeseries of the event. it returns an empty map if there are not
     * timeseries.
     *
     * @return timeseries.
     *
     */
    public Map<String, Object> getTimeseries() {
        return timeseries;
    }

    /**
     * returns Object Id of the object to post the event. It could be null.
     *
     * @return attributes.
     *
     */
    public String getObjectId() {
        if (smartObject == null || smartObject.getObjectId() == null) {
            return null;
        }
        return smartObject.getObjectId().toString();
    }

    /**
     * returns eventId of the event. this could be null if it was not defined.
     *
     * @return attributes.
     *
     */
    public UUID getEventId() {
        return eventId;
    }

    /**
     * returns smartObjectId of the event. It could be null if the object was not defined.
     *
     * @return attributes.
     *
     */
    public SmartObject getObject() {
        return smartObject;
    }

    /**
     * returns event type of the event.
     *
     * @return attributes.
     *
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * returns timestamp of the event.
     *
     * @return timestamp.
     *
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

}
