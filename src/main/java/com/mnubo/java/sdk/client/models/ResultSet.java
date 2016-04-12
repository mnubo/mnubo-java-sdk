package com.mnubo.java.sdk.client.models;

import java.util.List;

/**
 * Result Set Interface.
 *
 * @author Guillaume Coallier
 * @since 2016/04/06
 */
public interface ResultSet extends Iterable<SearchRow> {

    class ColumnDefinition {

        private String label;
        private String highLevelType;
        private String dataType;

        ColumnDefinition(String label, String highLevelType, String dataType) {
            this.label = label;
            this.highLevelType = highLevelType;
            this.dataType = dataType;
        }

        public ColumnDefinition(String label, String highLevelType) {
            this.label = label;
            this.highLevelType = highLevelType;
            setDataTypeFromHighLevelType(highLevelType);
        }

        public String getLabel() {
            return label;
        }

        public String getHighLevelType() {
            return highLevelType;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setHighLevelType(String highLevelType) {
            this.highLevelType = highLevelType;
            setDataTypeFromHighLevelType(highLevelType);
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        private void setDataTypeFromHighLevelType(String aHighLevelType) {
            try {
                this.dataType = Type.fromString(aHighLevelType).getDatatype();
            }
            catch (Exception e) {
                this.dataType = "unknown";
            }
        }
    }

    List<ColumnDefinition> getColumnDefinitions();

    boolean isExhausted();

    SearchRow one();

    List<SearchRow> all();
}