package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;

import java.util.HashSet;
import java.util.Set;

/**
 * DataSet Bean. To build a DataSet you must use the DatasetBuilder with the static method
 * DataSet.builder()
 *
 * @author Guillaume Coallier
 * @since 2016/04/105
 */
public final class DataSet {

    private final String key;
    private final String description;
    private final String displayName;
    private final Set<Field> fields;

    DataSet(String key, String description, String displayName, Set<Field> fields) {
        this.key = key;
        this.description = description;
        this.displayName = displayName;
        this.fields = fields;
    }

    /**
     * Builder, returns a DatasetBuilder to build immutable Dataset instances.
     *
     * @return Dataset Builder.
     */
    public static DatasetBuilder builder() {
        return new DatasetBuilder();
    }

    /**
     * DatasetBuilder nested class, this static Class builds immutable Dataset instances.
     * this needs to be requested with "DataSet.builder() method".
     *
     */
    public static class DatasetBuilder {

        private String key;
        private String description;
        private String displayName;
        private Set<Field> fields = new HashSet<Field>();

        DatasetBuilder() {
        }

        /**
         * Add key to the Dataset. Note that this parameter is mandatory.
         *
         * @param key: key of the Dataset.
         * @return DatasetBuilder: current Dataset builder.
         * @throws IllegalStateException a run exception will be launched if this field is
         * not present
         *
         */
        public DatasetBuilder withKey(String key) throws IllegalStateException {
            this.key = key;
            return this;
        }

        /**
         * Add a description to the Dataset.
         *
         * @param description: description of the Dataset.
         * @return DatasetBuilder: current Dataset builder.
         *
         */
        public DatasetBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Add a display name to the Dataset.
         *
         * @param displayName: name displayed.
         * @return DatasetBuilder: current Dataset builder.
         *
         */
        public DatasetBuilder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Add a display name to the Dataset.
         *
         * @param fields: fields of the dataset.
         * @return DatasetBuilder: current Dataset builder.
         *
         */
        public DatasetBuilder withFields(Set<Field> fields) {
            this.fields = fields;
            return this;
        }

        /**
         * Add an attribute to the Owner.
         *
         * @param field: add the field to the set of field of the DataSet.
         * @return OwnerBuilder: current Owner builder.
         *
         */
        public DatasetBuilder withAddedField(Field field) {
            this.fields.add(field);
            return this;
        }

        /**
         * Build the immutable Dataset instance with the parameters set.
         *
         * @return Dataset: immutable Dataset instance built
         */
        public DataSet build() {
            return new DataSet(key, description, displayName, fields);
        }

    }

    /**
     * returns the key of the Dataset.
     *
     * @return key.
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * returns the description of the Dataset.
     *
     * @return description.
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the displayName of the Dataset.
     *
     * @return displayName.
     *
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * returns the fields of the Dataset.
     *
     * @return fields.
     *
     */
    public Set<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String("key", key));
        toPrint.append(line2String("description", description));
        toPrint.append(line2String("displayName", displayName));
        for (Field f : fields) {
            toPrint.append(line2String(f.description, f.toString()));
        }
        toPrint.append("}\n");
        return toPrint.toString();
    }

    private String line2String(String name, Object value) {
        StringBuilder build = new StringBuilder();
        if (name != null) {
            build.append("     " + name + " : ");
            if (value != null) {
                build.append(value);
            }
            else {
                build.append(PRINT_OBJECT_NULL);
            }
            build.append("\n");
        }
        return build.toString();
    }

}
