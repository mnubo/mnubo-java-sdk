package com.mnubo.java.sdk.client.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by Guillaume Coallier on 04/07/16.
 */
public class FieldTest {

    @Test
    public void builder() {

        Field fieldBuilderTest = Field.builder().withContainerType(ContainerType.list)
                                                .withDescription("Field builder test")
                                                .withDisplayName("FieldBT")
                                                .withHighLevelType("Double")
                                                .withKey("FBT")
                                                .withPrimaryKey(true)
                                                .build();

        assertTrue(fieldBuilderTest.getContainerType().equals( ContainerType.list ));
        assertTrue(fieldBuilderTest.getDescription().equals("Field builder test"));
        assertTrue(fieldBuilderTest.getDisplayName().equals("FieldBT"));
        assertTrue(fieldBuilderTest.getHighLevelType().equals("Double"));
        assertTrue(fieldBuilderTest.getKey().equals("FBT"));
        assertTrue(fieldBuilderTest.isPrimaryKey() == true);
    }

    @Test
    public void ContainerTypeNull() {

        Field fieldContainerTest = Field.builder().withContainerType(null)
                                                  .withDescription("Field container test")
                                                  .withDisplayName("FieldCT")
                                                  .withHighLevelType("Long")
                                                  .withKey("FCT")
                                                  .withPrimaryKey(false)
                                                  .build();

        assertTrue(fieldContainerTest.getContainerType() == ContainerType.none);
        assertTrue(fieldContainerTest.getDescription().equals("Field container test"));
        assertTrue(fieldContainerTest.getDisplayName().equals("FieldCT"));
        assertTrue(fieldContainerTest.getHighLevelType().equals("Long"));
        assertTrue(fieldContainerTest.getKey().equals("FCT"));
        assertTrue(fieldContainerTest.isPrimaryKey() == false);
    }
}
