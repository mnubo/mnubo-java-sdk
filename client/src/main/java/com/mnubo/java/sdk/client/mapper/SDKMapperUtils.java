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

package com.mnubo.java.sdk.client.mapper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

/**
 * Event Bean. To build an event you must ask for EventBuilder using: the static method
 * event.builder()
 *
 * @author Mauro Arias
 * @since 2015/07/22
 */
public abstract class SDKMapperUtils {
    private static ObjectMapper objectMapper = SDKObjectMapperConfig.getObjectMapper();

    /**
     * Deserialize a String to a specific class type
     *
     * @param <T> This describes the type parameter
     * @param content: String to deserialize
     * @param valueType; Class type
     *
     * @return object instance deserialized of the type defined in valueType
     *
     * @throws IllegalStateException if any exception occurs.
     */
    public static <T> T readValue(String content, Class<T> valueType) throws IllegalStateException {
        try {
            return objectMapper.readValue(content, valueType);
        }
        catch (Exception e) {
            throw new IllegalStateException("reading json content", e.getCause());
        }
    }

    /**
     * Deserialize a String to a specific reference as a list type
     *
     * @param <T> This describes the type parameter
     * @param content: String to deserialize
     * @param valueType; type reference class type, for example use this for a list.
     *
     * @return object instance deserialized of the type defined in valueType
     *
     * @throws IllegalStateException if any exception occurs.
     */
    public static <T> T readValue(String content, TypeReference<T> valueType) throws IllegalStateException {
        try {
            return objectMapper.readValue(content, valueType);
        }
        catch (Exception e) {
            throw new IllegalStateException("reading json content", e.getCause());
        }
    }

    /**
     * Deserialize a String to a specific map
     *
     * @param <V> This describes the hey's map type parameter
     * @param <T> This describes the value's map type parameter
     * @param content: String to deserialize
     * @param keyType; type of the key's map
     * @param valueType; type of the value's map
     *
     * @return Map object instance deserialized of the type defined in keyType valueType
     *
     * @throws IllegalStateException if any exception occurs.
     */
    public static <V, T> Map<V, T> readValuesAsMap(String content, Class<V> keyType, Class<T> valueType)
            throws IllegalStateException {
        try {
            MapType stringStringMapTypew = MapType.construct(HashMap.class, SimpleType.construct(keyType),
                    SimpleType.construct(valueType));
            return (Map<V, T>) objectMapper.readValue(content, stringStringMapTypew);
        }
        catch (Exception e) {
            throw new IllegalStateException("reading json content", e.getCause());
        }
    }

    /**
     * Deserialize a String to a specific map, using jsonparser
     *
     * @param <V> This describes the hey's map type parameter
     * @param <T> This describes the value's map type parameter
     * @param parser: json parser to deserialize
     * @param keyType; type of the key's map
     * @param valueType; type of the value's map
     *
     * @return Map object instance deserialized of the type defined in keyType valueType
     *
     * @throws IllegalStateException if any exception occurs.
     */
    public static <V, T> Map<V, T> readValuesAsMap(JsonParser parser, Class<V> keyType, Class<T> valueType)
            throws IllegalStateException {
        try {
            MapType stringMapTypew = MapType.construct(HashMap.class, SimpleType.construct(keyType),
                    SimpleType.construct(valueType));
            return (HashMap<V, T>) objectMapper.readValue(parser, stringMapTypew);
        }
        catch (Exception e) {
            throw new IllegalStateException("reading json content", e.getCause());
        }

    }

}
