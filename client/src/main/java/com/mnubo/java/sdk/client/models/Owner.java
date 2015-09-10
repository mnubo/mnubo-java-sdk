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

import org.joda.time.DateTime;

/**
 * Owner Bean. To build an Owner you must ask for OwnerBuilder using: the static method
 * Owner.builder()
 *
 * @author Mauro Arias
 * @since 2015/08/10
 */
public class Owner {

    /**
     * Registration_date Constant key used during the deserialization & serialization of
     * json files.
     */
    public static final String REGISTRATION_DATE = "x_registration_date";

    /**
     * password Constant key used during the deserialization & serialization of json
     * files.
     */
    public static final String PASSWORD = "x_password";

    /**
     * username Constant key used during the deserialization & serialization of json
     * files.
     */
    public static final String USERNAME = "username";

    private String username;
    private String password;
    private DateTime registrationDate = now();
    private Map<String, Object> attributes = new HashMap<String, Object>();

    Owner(String username, String password, DateTime registrationDate, Map<String, Object> attributes) {
        validIsBlank(username, "usermame cannot be null or empty");
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.attributes = attributes;
    }

    /**
     * Builder, returns a OwnerBuilder to build immutable Owner instances.
     *
     * @return Owner Builder.
     */
    public static OwnerBuilder builder() {
        return new OwnerBuilder();
    }

    /**
     * OwnerBuilder nested class, this static Class builds immutable owner instances. this
     * needs to be requested with "Owner.builder() method".
     *
     */
    public static class OwnerBuilder {

        private String username;
        private String password;
        private DateTime registrationDate = now();
        private Map<String, Object> attributes = new HashMap<String, Object>();

        OwnerBuilder() {

        }

        /**
         * Add an user name to the Owner. Note that this parameter is mandatory.
         *
         * @param username: user name of the owner.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        /**
         * Add a password to the owner.
         *
         * @param password: password of the owner.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Add a registrationDate to the owner.
         *
         * @param registrationDate: registrationDate of the owner.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withRegistrationDate(DateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        /**
         * Add a list of attributes to the Owner.
         *
         * @param attributes: Map of attributes.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Add an attribute to the Owner.
         *
         * @param key: attribute key or name.
         * @param value: attribute value.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public OwnerBuilder withAddedAttribute(String key, Object value) {
            this.attributes.put(key, value);
            return this;
        }

        /**
         * Build the immutable Owner instance with the parameters set. Note that username
         * parameter is mandatory.
         *
         */
        public Owner build() {
            return new Owner(username, password, registrationDate, attributes);
        }

    }

    /**
     * returns attribute of the Owner. It returns an empty map if there are not
     * attributes.
     *
     * @return attributes.
     *
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * returns the user name.
     *
     * @return UserName.
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the password.
     *
     * @return password.
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * returns the registration date.
     *
     * @return registration date.
     *
     */
    public DateTime getRegistrationDate() {
        return registrationDate;
    }

}
