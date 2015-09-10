/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Jul 22, 2015
 *
 * ---------------------------------------------------------------------------
 */
package com.mnubo.java.sdk.client.spi;

import java.util.List;

import com.mnubo.java.sdk.client.models.Event;

/**
 * Event SDK Client. This interface gives access to handle events in specific objects.
 *
 * @author Mauro Arias
 * @since 2015/07/22
 */
public interface EventsSDK {

    /**
     * Allows post events to an unique object.
     *
     * @param ObjectId, device Id to send the events
     * @param events, list the events to be posted.
     */
    void send(String ObjectId, List<Event> events);

    /**
     * Allows post events to several objects. In this case, the device id is taken
     * directly from each event. Thus, a post will be sent by each event in the list.
     *
     * @param events, list the events to be posted.
     */
    void send(List<Event> events);
}
