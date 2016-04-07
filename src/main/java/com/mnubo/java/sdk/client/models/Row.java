package com.mnubo.java.sdk.client.models;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.mnubo.java.sdk.client.models.ResultSet.ColumnDefinition;

/**
 * Row Interface.
 *
 * @author Guillaume Coallier
 * @since 2016/04/06
 */
public interface Row {

    List<ColumnDefinition> getColumnDefinitions();

    boolean isNull(int i);
    boolean isNull(String name);

    String getString(String name);
    String getString(int i);
    int getInt(String name);
    int getInt(int i);
    long getLong(String name);
    long getLong(int i);
    double getDouble(String name);
    double getDouble(int i);
    float getFloat(String name);
    float getFloat(int i);
    boolean getBoolean(String name);
    boolean getBoolean(int i);
    DateTime getDateTime(String name);
    DateTime getDateTime(int i);
    UUID getUUID(String name);
    UUID getUUID(int i);

    List<String> getStringList(String name);
    List<String> getStringList(int i);
    List<Integer> getIntList(String name);
    List<Integer> getIntList(int i);
    List<Long> getLongList(String name);
    List<Long> getLongList(int i);
    List<Double> getDoubleList(String name);
    List<Double> getDoubleList(int i);
    List<Float> getFloatList(String name);
    List<Float> getFloatList(int i);
    List<Boolean> getBooleanList(String name);
    List<Boolean> getBooleanList(int i);
    List<DateTime> getDateTimeList(String name);
    List<DateTime> getDateTimeList(int i);
    List<UUID> getUUIDList(String name);
    List<UUID> getUUIDList(int i);

}