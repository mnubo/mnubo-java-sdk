/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Jul 27, 2015
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.spi;

import com.mnubo.java.sdk.client.models.Owner;

/**
 * Owner SDK Client. This interface gives access to handle Owners.
 *
 * @author Mauro Arias
 * @since 2015/07/22
 */
public interface OwnersSDK {

    /**
     * Allows create owners.
     *
     * @param owner, Owner bean to be created.
     */
    void create(Owner owner);

    /**
     * Allows an owner claim an Object.
     *
     * @param username, Owner's username how claims the object.
     * @param deviceId, Object's deviceId of the Object claimed.
     */
    void claim(String username, String deviceId);

}
