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

import com.mnubo.java.sdk.client.models.SmartObject;

/**
 * Event SDK Client. This interface gives access to handle objects.
 *
 * @author Mauro Arias
 * @since 2015/07/22
 */
public interface ObjectsSDK {

    /**
     * Allows create SmartObjects.
     *
     * @param object, SmartObject bean to be created.
     */
    void create(SmartObject object);
}
