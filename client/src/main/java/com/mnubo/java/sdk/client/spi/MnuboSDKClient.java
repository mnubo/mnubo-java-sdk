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

/**
 * Mnubo SDK Client. This interface gives access to all mnubo's sdk clients.
 *
 * @author Mauro Arias
 * @since 2015/07/22
 */
public interface MnuboSDKClient {

    /**
     * Returns SmartObject Client, giving access to handle mnubo smartObjects.
     *
     * @return ObjectSDK interface.
     *
     */
    ObjectsSDK getObjectClient();

    /**
     * Returns Event Client, giving access to handle mnubo events.
     *
     * @return EventSDK interface
     *
     */
    EventsSDK getEventClient();

    /**
     * Returns Owner Client, giving access to handle mnubo Owners.
     *
     * @return OwnerSDK interface
     *
     */
    OwnersSDK getOwnerClient();

}
