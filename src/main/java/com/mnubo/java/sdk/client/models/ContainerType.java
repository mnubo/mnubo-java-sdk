package com.mnubo.java.sdk.client.models;

import static java.util.Locale.US;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by dmasse on 2016-02-08.
 */
public enum ContainerType {
    none, list;

    public static ContainerType fromString(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        return valueOf(StringUtils.lowerCase(name, US));
    }

    public boolean is(ContainerType containerType) {
        return this.equals(containerType);
    }
}