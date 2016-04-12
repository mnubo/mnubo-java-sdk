package com.mnubo.java.sdk.client.spi;

import java.util.List;

import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.SearchResultSet;

/**
 * Owner SDK Client. This interface gives access to handle Search APIs.
 *
 * @author Guillaume Coallier
 * @since 2016/04/05
 */
public interface SearchSDK {

    /**
     * Use the Search API to perform a wide range of analytics on your data. You can query
     * information and perform analytics on Owners, Objects, and Events using SmartObjects
     * queries. Search API queries are generally performed on the client server for
     * aggregate and individual (mobile apps) analytics.
     *
     * @param query, Query to be process.
     * 
     * @return SearchResultSet: Result Set of the basic Search.
     */
    SearchResultSet search(String query);

    /**
     * The search API allows for a query of the dataset list. 
     * This list is in JSON format and describes what can be 
     * queried for objects, events, and owners.
     *
     * No parameter
     * 
     * @return DataSet: List of DataSet return by the Search Dataset
     */
    List<DataSet> getDatasets();

}