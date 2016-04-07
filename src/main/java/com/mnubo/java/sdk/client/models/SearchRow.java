package com.mnubo.java.sdk.client.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.mnubo.java.sdk.client.models.ResultSet.ColumnDefinition;
import com.mnubo.java.sdk.client.models.SearchResult.Column;

/**
 * Implementation of the Row for the Search API.
 *
 * @author Guillaume Coallier
 * @since 2016/04/06
 */
public class SearchRow implements Row {

    private List<ColumnDefinition> columnsMetadata;
    private List<Object> rows;

    public List<ColumnDefinition> getColumnsMetadata() {
        return columnsMetadata;
    }

    public void setColumnsMetadata(List<ColumnDefinition> columnsMetadata) {
        this.columnsMetadata = columnsMetadata;
    }

    public void setColumns(List<Column> columns) {
        List<ColumnDefinition> columnDefinitions = new ArrayList<>();

        for (Column column : columns) {
            columnDefinitions.add(new ColumnDefinition(column.getLabel(), column.getType()));
        }

        this.columnsMetadata = columnDefinitions;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

    @Override
    public List<ColumnDefinition> getColumnDefinitions() {
        return columnsMetadata;
    }

    @Override
    public boolean isNull(int i) {
        return (rows.get(i) == null);
    }

    @Override
    public boolean isNull(String name) {
        return (getRowValueWithColumnName(name) == null);
    }

    @Override
    public String getString(String name) {
        return (String) getRowValueWithColumnName(name);
    }

    @Override
    public String getString(int i) {
        return (String) rows.get(i);
    }

    @Override
    public int getInt(String name) {
        return (int) getRowValueWithColumnName(name);
    }

    @Override
    public int getInt(int i) {
        return (int) rows.get(i);
    }

    @Override
    public long getLong(String name) {
        return (long) getRowValueWithColumnName(name);
    }

    @Override
    public long getLong(int i) {
        return (long) rows.get(i);
    }

    @Override
    public double getDouble(String name) {
        return (double) getRowValueWithColumnName(name);
    }

    @Override
    public double getDouble(int i) {
        return (double) rows.get(i);
    }

    @Override
    public float getFloat(String name) {
        return (float) getRowValueWithColumnName(name);
    }

    @Override
    public float getFloat(int i) {
        return (float) rows.get(i);
    }

    @Override
    public boolean getBoolean(String name) {
        return (boolean) getRowValueWithColumnName(name);
    }

    @Override
    public boolean getBoolean(int i) {
        return (boolean) rows.get(i);
    }

    @Override
    public DateTime getDateTime(String name) {
        return (DateTime) getRowValueWithColumnName(name);
    }

    @Override
    public DateTime getDateTime(int i) {
        return (DateTime) rows.get(i);
    }

    @Override
    public UUID getUUID(String name) {
        return (UUID) getRowValueWithColumnName(name);
    }

    @Override
    public UUID getUUID(int i) {
        return (UUID) rows.get(i);
    }

    @Override
    public List<String> getStringList(String name) {
        return (List<String>) getRowValueWithColumnName(name);
    }

    @Override
    public List<String> getStringList(int i) {
        return (List<String>) rows.get(i);
    }

    @Override
    public List<Integer> getIntList(String name) {
        return (List<Integer>) getRowValueWithColumnName(name);
    }

    @Override
    public List<Integer> getIntList(int i) {
        return (List<Integer>) rows.get(i);
    }

    @Override
    public List<Long> getLongList(String name) {
        return (List<Long>) getRowValueWithColumnName(name);
    }

    @Override
    public List<Long> getLongList(int i) {
        return (List<Long>) rows.get(i);
    }

    @Override
    public List<Double> getDoubleList(String name) {
        return (List<Double>) getRowValueWithColumnName(name);
    }

    @Override
    public List<Double> getDoubleList(int i) {
        return (List<Double>) rows.get(i);
    }

    @Override
    public List<Float> getFloatList(String name) {
        return (List<Float>) getRowValueWithColumnName(name);
    }

    @Override
    public List<Float> getFloatList(int i) {
        return (List<Float>) rows.get(i);
    }

    @Override
    public List<Boolean> getBooleanList(String name) {
        return (List<Boolean>) getRowValueWithColumnName(name);
    }

    @Override
    public List<Boolean> getBooleanList(int i) {
        return (List<Boolean>) rows.get(i);
    }

    @Override
    public List<DateTime> getDateTimeList(String name) {
        return (List<DateTime>) getRowValueWithColumnName(name);
    }

    @Override
    public List<DateTime> getDateTimeList(int i) {
        return (List<DateTime>) rows.get(i);
    }

    @Override
    public List<UUID> getUUIDList(String name) {
        return (List<UUID>) getRowValueWithColumnName(name);
    }

    @Override
    public List<UUID> getUUIDList(int i) {
        return (List<UUID>) rows.get(i);
    }

    public Object getRowValueWithColumnName(String name) {

        for (int i = 0; i < columnsMetadata.size(); i++) {
            if (columnsMetadata.get(i).getLabel().equalsIgnoreCase(name)) {
                return rows.get(i);
            }
        }

        return null;
    }

}
