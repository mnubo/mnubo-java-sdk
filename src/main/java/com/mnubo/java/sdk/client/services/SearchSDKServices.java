package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.utils.ValidationUtils.validNotNull;

import java.util.ArrayList;
import java.util.List;

import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.SearchResult;
import com.mnubo.java.sdk.client.models.SearchResultSet;
import com.mnubo.java.sdk.client.models.SearchRow;
import com.mnubo.java.sdk.client.spi.SearchSDK;

class SearchSDKServices implements SearchSDK {

    public static final String BASIC_SEARCH_PATH = "/search/basic";
    public static final String DATASETS_SEARCH_PATH = "/search/datasets";

    private final SDKService sdkCommonServices;

    SearchSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    public SDKService getSdkCommonServices() {
        return sdkCommonServices;
    }

    @Override
    public SearchResultSet search(String query) {
        // url
        final String url = sdkCommonServices.getRestitutionBaseUri().path(BASIC_SEARCH_PATH).build().toString();

        validNotNull(query, "Query body");

        // posting
        SearchResult searchResult = sdkCommonServices.postRequest(url, SearchResult.class, query);

        if(searchResult == null) {
            return null;
        }

        // Transform SearchResult To SearchResultSet
        SearchResultSet searchResultSet = new SearchResultSet();
        
        searchResultSet.setColumns(searchResult.getColumns());
        
        List<SearchRow> allSearchRow = new ArrayList<>();
        
        for (List<Object> l : searchResult.getRows()) {
            SearchRow searchRow = new SearchRow();
            searchRow.setColumns(searchResult.getColumns());
            searchRow.setRows(l);
            allSearchRow.add(searchRow);
        }

        searchResultSet.setRowsMetadata(allSearchRow);

        return searchResultSet;
    }

    @Override
    public List<DataSet> getDatasets() {
        // url
        final String url = sdkCommonServices.getRestitutionBaseUri().path(DATASETS_SEARCH_PATH).build().toString();

        // posting
        return sdkCommonServices.getRequest(url, List.class);
    }
}