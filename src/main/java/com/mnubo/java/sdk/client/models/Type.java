/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: Guillaume Coallier Date : April 12, 2016
 *
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.models;

import static java.util.Locale.US;
import static org.apache.commons.lang3.StringUtils.upperCase;

import org.apache.commons.lang3.StringUtils;

public enum Type {

    TIME("INT"),
    DATETIME("STRING"),
    LONG("LONG"),
    INT("INT"),
    VOLUME("DOUBLE"),
    DOUBLE("DOUBLE"),
    ACCELERATION("DOUBLE"),
    TEXT("STRING"),
    BOOLEAN("BOOLEAN"),
    SPEED("DOUBLE"),
    STATE("STRING"),
    MASS("FLOAT"),
    EMAIL("STRING"),
    TEMPERATURE("DOUBLE"),
    FLOAT("FLOAT"),
    AREA("DOUBLE"),
    LENGTH("DOUBLE"),
    COUNTRY_ISO("STRING"),
    PREDICTIVE_MODEL("BLOB"),
    TIME_ZONE("STRING");

    private String datatype;

    Type(String aDatatype) {
        this.datatype = aDatatype;
    }

    public static Type fromString(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        return valueOf(upperCase(name, US));
    }

    public boolean is(Type type) {
        return this.equals(type);
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
