package com.mnubo.java.sdk.client.models;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * Created by Guillaume Coallier on 04/07/16.
 */
public class DataSetTest {

    @Test
    public void builder() {

        Set<Field> fields = new HashSet<Field>();

        fields.add(Field.builder().withContainerType(ContainerType.list)
                                  .withDescription("Field unit test 1")
                                  .withDisplayName("FieldT1")
                                  .withHighLevelType("String")
                                  .withKey("FT1")
                                  .withPrimaryKey(true)
                                  .build());

        fields.add(Field.builder().withContainerType(ContainerType.none)
                                  .withDescription("Field unit test 2")
                                  .withDisplayName("FieldT2")
                                  .withHighLevelType("Boolean")
                                  .withKey("FT2")
                                  .withPrimaryKey(true)
                                  .build());

        DataSet datasetUnitTest = DataSet.builder()
                                 .withDescription("DataSet Test 1")
                                 .withDisplayName("DataSet1")
                                 .withKey("KeyData1")
                                 .withFields(fields)
                                 .build();
        
        assertThat(datasetUnitTest.getFields(), is(equalTo(fields)));
        assertTrue(datasetUnitTest.getDescription().equals("DataSet Test 1"));
        assertTrue(datasetUnitTest.getDisplayName().equals("DataSet1"));
        assertTrue(datasetUnitTest.getKey().equals("KeyData1"));
    }

    @Test
    public void fieldsNull() {

        DataSet datasetUnitTest = DataSet.builder()
                .withDescription("DataSet Test 1")
                .withDisplayName("DataSet1")
                .withKey("KeyData1")
                .withFields(null)
                .build();

        assertTrue(datasetUnitTest.getFields() == null);
    }

    @Test
    public void addFields() {
        
        Set<Field> fields = new HashSet<Field>();
        
        Field addedField1 = Field.builder().withContainerType(ContainerType.list)
                .withDescription("Field unit test 3")
                .withDisplayName("FieldT3")
                .withHighLevelType("String")
                .withKey("FT3")
                .withPrimaryKey(true)
                .build();
        
        Field addedField2 = Field.builder().withContainerType(ContainerType.none)
                .withDescription("Field unit test 4")
                .withDisplayName("FieldT4")
                .withHighLevelType("Boolean")
                .withKey("FT4")
                .withPrimaryKey(false)
                .build();

        fields.add(addedField1);
        fields.add(addedField2);

        DataSet datasetUnitTest = DataSet.builder()
                .withAddedField(addedField1)
                .withAddedField(addedField2)
                .build();

        assertThat(datasetUnitTest.getFields(), is(equalTo(fields)));
        assertTrue(datasetUnitTest.getDescription() == null);
        assertTrue(datasetUnitTest.getDisplayName() == null);
        assertTrue(datasetUnitTest.getKey() == null);
    }
}
