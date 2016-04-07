package com.mnubo.java.sdk.client.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.mnubo.java.sdk.client.models.SearchResult.Column;

/**
 * Search Result Set Interface. 
 * Use to get Result from the Search API 
 *
 * @author Guillaume Coallier
 * @since 2016/04/06
 */
public class SearchResultSet implements ResultSet {

    protected List<ColumnDefinition> columnsMetadata;

    protected List<SearchRow> rowsMetadata;

    protected Queue<SearchRow> queueRows = new ArrayDeque<SearchRow>();

    @Override
    public List<ColumnDefinition> getColumnDefinitions() {
        return columnsMetadata;
    }

    public void setColumnsMetadata(List<ColumnDefinition> columnsMetadata) {
        this.columnsMetadata = columnsMetadata;
    }

    public void setColumns(List<Column> columns) {
        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        
        for( Column column: columns) {
            columnDefinitions.add(new ColumnDefinition(column.getLabel(), column.getType()));
        }

        this.columnsMetadata = columnDefinitions;
    }

    public List<SearchRow> getRowsData() {
        return rowsMetadata;
    }

    public void setRowsMetadata(List<SearchRow> rowsMetadata) {
        this.rowsMetadata = rowsMetadata;
        // Add all the row to the Queue for the iterator
        for (SearchRow searchRow : rowsMetadata) {
            queueRows.add(searchRow);
        }
    }

    @Override
    public Iterator<SearchRow> iterator() {
        return new Iterator<SearchRow>() {

            @Override
            public boolean hasNext() {
                return !isExhausted();
            }

            @Override
            public SearchRow next() {
                return SearchResultSet.this.one();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean isEmpty() {
        return rowsMetadata.isEmpty();
    }

    @Override
    public SearchRow one() {
        return queueRows.poll();
    }

    @Override
    public boolean isExhausted() {
        return queueRows.isEmpty();
    }

    @Override
    public List<SearchRow> all() {
        if (isExhausted()) {
            return Collections.emptyList();
        }

        List<SearchRow> result = new ArrayList<SearchRow>();
        for (SearchRow row : rowsMetadata) {
            result.add(row);
        }

        return result;
    }
}
