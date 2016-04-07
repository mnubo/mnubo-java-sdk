package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.services.SearchSDKServices.BASIC_SEARCH_PATH;
import static com.mnubo.java.sdk.client.services.SearchSDKServices.DATASETS_SEARCH_PATH;
import static java.lang.String.format;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.mnubo.java.sdk.client.models.ContainerType;
import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.Field;
import com.mnubo.java.sdk.client.models.ResultSet.ColumnDefinition;
import com.mnubo.java.sdk.client.models.SearchResult;
import com.mnubo.java.sdk.client.models.SearchResult.Column;
import com.mnubo.java.sdk.client.models.SearchResultSet;
import com.mnubo.java.sdk.client.models.SearchRow;
import com.mnubo.java.sdk.client.spi.SearchSDK;

/**
 * Created by Guillaume Coallier on 04/06/16.
 */
public class SearchSDKServicesTest extends AbstractServiceTest {

    private static SearchSDK searchClient;

    // Query example (not used due to mock of RestTemplate)
    private static final String query = "{ \"from\": \"event\", \"select\": [ {\"value\": \"speed\"} ] }";

    protected static ResponseEntity httpResponse = mock(ResponseEntity.class);

    private static SearchResultSet searchResultSet;
    private static List<DataSet> datasets;
    private static SearchResult searchResult;
    private static List<List<Object>> searchResultRows;

    @BeforeClass
    public static void searchSDKSetup() {
        searchClient = getClient().getSearchClient();

        // Create Field Data
        Set<Field> fields = new HashSet<Field>();
        fields.add(Field.builder().withContainerType(ContainerType.list).withDescription("Field test 1")
                .withDisplayName("Field1").withHighLevelType("TEXT").withKey("F1").withPrimaryKey(true)
                .build());
        fields.add(Field.builder().withContainerType(ContainerType.list).withDescription("Field test 2")
                .withDisplayName("Field2").withHighLevelType("BOOLEAN").withKey("F2").withPrimaryKey(true)
                .build());

        // Create data for DataSets with the Field data
        datasets = new ArrayList<>();
        datasets.add(DataSet.builder().withDescription("DataSet Test 1").withDisplayName("DataSet1").withKey("KeyData1")
                .withFields(fields).build());
        datasets.add(DataSet.builder().withDescription("DataSet Test 2").withDisplayName("DataSet2").withKey("KeyData2")
                .withFields(fields).build());

        // Create data for the Column Definition
        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(new ColumnDefinition("column1", "TEXT"));
        columnDefinitions.add(new ColumnDefinition("column2", "BOOLEAN"));
        columnDefinitions.add(new ColumnDefinition("columnTest3", "DOUBLE"));

        List<Column> columns = new ArrayList<>();
        columns.add(new Column("column1", "TEXT"));
        columns.add(new Column("column2", "BOOLEAN"));
        columns.add(new Column("columnTest3", "DOUBLE"));

        // Create data for Search Result
        searchResult = new SearchResult();
        searchResult.setColumns(columns);
        searchResultRows = new ArrayList<>();
        searchResultRows.add(searchResultRow("test String 1", true, 24.66));
        searchResultRows.add(searchResultRow("test String 2", true, 0.25));
        searchResultRows.add(searchResultRow("test String 3", false, 100.99));
        searchResultRows.add(searchResultRow("test String 4", true, 44.00));
        searchResultRows.add(searchResultRow("test String 5", false, 66.777));
        searchResult.setRows(searchResultRows);

        // Create data for Search Result Set
        searchResultSet = new SearchResultSet();
        searchResultSet.setColumnsMetadata(columnDefinitions);
        List<SearchRow> searchResultSetRows = new ArrayList<>();
        addSearchResultSetRow("test String 1", true, 24.66, columnDefinitions, searchResultSetRows);
        addSearchResultSetRow("test String 2", true, 0.25, columnDefinitions, searchResultSetRows);
        addSearchResultSetRow("test String 3", false, 100.99, columnDefinitions, searchResultSetRows);
        addSearchResultSetRow("test String 4", true, 44.00, columnDefinitions, searchResultSetRows);
        addSearchResultSetRow("test String 5", false, 66.777, columnDefinitions, searchResultSetRows);
        searchResultSet.setRowsMetadata(searchResultSetRows);
    }

    @Test
    public void searchBasicThenOk() {

        // Mock Call to POST Search
        when(restTemplate.postForObject(any(String.class), any(Object.class), eq(SearchResult.class)))
                .thenReturn(searchResult);

        String url = getClient().getSdkService().getRestitutionBaseUri().path(BASIC_SEARCH_PATH).build().toString();
        assertThat(url, is(equalTo(format("https://%s:443/api/v3/search/basic", HOST))));
        SearchResultSet testSearchResultSet = searchClient.search(query);

        for (int i = 0; i < testSearchResultSet.getColumnDefinitions().size(); i++) {
            assertThat(testSearchResultSet.getColumnDefinitions().get(i).getLabel(),
                    is(equalTo(searchResultSet.getColumnDefinitions().get(i).getLabel())));
            assertThat(testSearchResultSet.getColumnDefinitions().get(i).getHighLevelType(),
                    is(equalTo(searchResultSet.getColumnDefinitions().get(i).getHighLevelType())));
        }

        for (int j = 0; j < testSearchResultSet.getColumnDefinitions().size(); j++) {
            assertThat(testSearchResultSet.getRowsData().get(j).getRows(),
                    is(equalTo(searchResultSet.getRowsData().get(j).getRows())));
        }
    }

    @Test
    public void searchBasicNullThenFail() {

        // Mock Call to POST Search
        when(restTemplate.postForObject(any(String.class), any(Object.class), eq(SearchResult.class)))
                .thenReturn(searchResult);

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Query body cannot be null.");
        SearchResultSet searchResultSet = searchClient.search(null);
    }

    @Test
    public void searchBasicReturnNullThenReturnNull() {

        // Mock Call to POST Search
        when(restTemplate.postForObject(any(String.class), any(Object.class), eq(SearchResult.class))).thenReturn(null);

        String url = getClient().getSdkService().getRestitutionBaseUri().path(BASIC_SEARCH_PATH).build().toString();
        assertThat(url, is(equalTo(format("https://%s:443/api/v3/search/basic", HOST))));
        SearchResultSet testSearchResultSet = searchClient.search(query);

        assertThat(testSearchResultSet, is(equalTo(null)));
    }

    @Test
    public void searchBasicGetSpecificSearchResultSetThenOk() {

        // Mock Call to POST Search
        when(httpResponse.getBody()).thenReturn(datasets);

        when(restTemplate.postForObject(any(String.class), any(Object.class), eq(SearchResult.class)))
                .thenReturn(searchResult);

        String url = getClient().getSdkService().getRestitutionBaseUri().path(BASIC_SEARCH_PATH).build().toString();
        assertThat(url, is(equalTo(format("https://%s:443/api/v3/search/basic", HOST))));
        SearchResultSet testSearchResultSet = searchClient.search(query);

        // Verify all columns for the first row (index 0) ("test String 1", true, 24.66)
        for (int i = 0; i < testSearchResultSet.getColumnDefinitions().size(); i++) {

            if (testSearchResultSet.getColumnDefinitions().get(i).getHighLevelType().equalsIgnoreCase("TEXT")) {

                // Verify the Datatype for TEXT is STRING
                assertThat(testSearchResultSet.getRowsData().get(0).getColumnDefinitions().get(i).getDataType(),
                        is(equalTo("STRING")));

                // Verify value of the label
                assertThat(
                        testSearchResultSet.getRowsData().get(0)
                                .getString(testSearchResultSet.getColumnDefinitions().get(i).getLabel()),
                        is(equalTo("test String 1")));
            }

            if (testSearchResultSet.getColumnDefinitions().get(i).getHighLevelType().equalsIgnoreCase("DOUBLE")) {

                // Verify the Datatype for DOUBLE is DOUBLE
                assertThat(testSearchResultSet.getRowsData().get(0).getColumnDefinitions().get(i).getDataType(),
                        is(equalTo("DOUBLE")));

                // Verify value of the label
                assertThat(testSearchResultSet.getRowsData().get(0)
                        .getDouble(testSearchResultSet.getColumnDefinitions().get(i).getLabel()), is(equalTo(24.66)));
            }

            if (testSearchResultSet.getColumnDefinitions().get(i).getHighLevelType().equalsIgnoreCase("BOOLEAN")) {

                // Verify the Datatype for BOOLEAN is BOOLEAN
                assertThat(testSearchResultSet.getRowsData().get(0).getColumnDefinitions().get(i).getDataType(),
                        is(equalTo("BOOLEAN")));

                // Verify value of the label
                assertThat(
                        testSearchResultSet.getRowsData().get(0)
                        .getBoolean(testSearchResultSet.getColumnDefinitions().get(i).getLabel()), is(equalTo(true)));
            }
        }
    }

    @Test
    public void searchBasicWithIteratorThenOk() {

        // Mock Call to POST Search
        when(restTemplate.postForObject(any(String.class), any(Object.class), eq(SearchResult.class)))
                .thenReturn(searchResult);

        String url = getClient().getSdkService().getRestitutionBaseUri().path(BASIC_SEARCH_PATH).build().toString();
        assertThat(url, is(equalTo(format("https://%s:443/api/v3/search/basic", HOST))));
        SearchResultSet testSearchResultSet = searchClient.search(query);

        while (testSearchResultSet.iterator().hasNext()) {
            SearchRow searchRow = testSearchResultSet.iterator().next();

            // Verify Column Definitions for each Search Row
            List<ColumnDefinition> columnDefinitions = searchRow.getColumnDefinitions();
            for (ColumnDefinition columnDefinition : columnDefinitions) {
                assertThat(columnDefinition.getLabel(), anyOf(is("column1"), is("column2"), is("columnTest3")));
                assertThat(columnDefinition.getHighLevelType(), anyOf(is("TEXT"), is("BOOLEAN"), is("DOUBLE")));
                assertThat(columnDefinition.getDataType(), anyOf(is("STRING"), is("BOOLEAN"), is("DOUBLE")));
            }

            // Verify Data For Each Row
            assertThat(searchRow.getString("column1"), anyOf(is("test String 1"), is("test String 2"),
                    is("test String 3"), is("test String 4"), is("test String 5")));
            assertThat(searchRow.getBoolean("column2"), anyOf(is(true), is(false)));
            assertThat(searchRow.getDouble("columnTest3"),
                    anyOf(is(24.66), is(0.25), is(100.99), is(44.00), is(66.777)));
        }
    }

    @Test
    public void searchDatasetsThenOk() {

        // Mock Call to GET DataSets
        when(httpResponse.getBody()).thenReturn(datasets);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(List.class)))
                .thenReturn(httpResponse);

        final String url = getClient().getSdkService().getRestitutionBaseUri().path(DATASETS_SEARCH_PATH)
                                      .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/search/datasets", HOST))));

        List<DataSet> testDatasets = searchClient.getDatasets();

        assertThat(testDatasets, is(equalTo(datasets)));
    }
    
    @Test
    public void searchDatasetsEmptyThenReturnNull() {

        // Mock Call to GET DataSets
        when(httpResponse.getBody()).thenReturn(null);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(List.class)))
                .thenReturn(httpResponse);

        final String url = getClient().getSdkService().getRestitutionBaseUri().path(DATASETS_SEARCH_PATH)
                                      .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/search/datasets", HOST))));

        List<DataSet> testDatasets = searchClient.getDatasets();

        assertThat(testDatasets, is(equalTo(null)));
    }

    public static void addSearchResultSetRow(Object value1, Object value2, Object value3,
            List<ColumnDefinition> columns, List<SearchRow> rows) {

        SearchRow sr = new SearchRow();
        sr.setColumnsMetadata(columns);
        List<Object> objectRow = new ArrayList<>();
        objectRow.add(value1);
        objectRow.add(value2);
        objectRow.add(value3);
        sr.setRows(objectRow);
        rows.add(sr);
    }

    public static List<Object> searchResultRow(Object value1, Object value2, Object value3) {

        List<Object> row = new ArrayList<>();
        row.add(value1);
        row.add(value2);
        row.add(value3);
        return row;
    }
}
