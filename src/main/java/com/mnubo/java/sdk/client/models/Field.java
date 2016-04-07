package com.mnubo.java.sdk.client.models;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;

/**
 * Field Bean. To build a Field you must use the FieldBuilder with the static method
 * Field.builder()
 *
 * @author Guillaume Coallier
 * @since 2016/04/105
 */
public final class Field {
    
    String key;
    String highLevelType;
    String displayName;
    String description;
    ContainerType containerType;
    boolean isPrimaryKey;

    Field(String key, String highLevelType, String displayName, String description, ContainerType containerType,
            boolean isPrimaryKey) {
        this.key = key;
        this.highLevelType = highLevelType;
        this.displayName = displayName;
        this.description = description;
        this.containerType = containerType == null ? ContainerType.none : containerType;
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * Builder, returns a FieldBuilder to build immutable Field instances.
     *
     * @return Field Builder.
     */
    public static FieldBuilder builder() {
        return new FieldBuilder();
    }

    /**
     * FieldBuilder nested class, this static Class builds immutable Field instances. this
     * needs to be requested with "Field.builder() method".
     *
     */
    public static class FieldBuilder {

        private String key;
        private String highLevelType;
        private String displayName;
        private String description;
        private ContainerType containerType;
        private boolean isPrimaryKey;

        FieldBuilder() {
        }

        /**
         * Add an key to the Field. Note that this parameter is mandatory.
         * @param key : Key of the Field.
         *
         * @return FieldBuilder: current Field builder.
         * @throws IllegalStateException a run exception will be launched if this field is
         * not present
         *
         */
        public FieldBuilder withKey(String key) throws IllegalStateException {
            this.key = key;
            return this;
        }

        /**
         * Add a highLevelType to the Field.
         *
         * @param highLevelType: high Level Type of the Field.
         * @return FieldBuilder: current Field builder.
         *
         */
        public FieldBuilder withHighLevelType(String highLevelType) {
            this.highLevelType = highLevelType;
            return this;
        }

        /**
         * Add a display Name to the Field.
         *
         * @param displayName: Display Name of the Field.
         * @return FieldBuilder: current Field builder.
         *
         */
        public FieldBuilder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Add a description to the Field.
         *
         * @param description: description of the Field.
         * @return FieldBuilder: current Field builder.
         *
         */
        public FieldBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Add a container Type to the Field.
         *
         * @param containerType: container Type of the Field (none or list)
         * @return FieldBuilder: current Field builder.
         *
         */
        public FieldBuilder withContainerType(ContainerType containerType) {
            this.containerType = containerType;
            return this;
        }

        /**
         * Add a container Type to the Field.
         *
         * @param isPrimaryKey: Specify if the key is a primary key
         * @return FieldBuilder: current Field builder.
         *
         */
        public FieldBuilder withPrimaryKey(boolean isPrimaryKey) {
            this.isPrimaryKey = isPrimaryKey;
            return this;
        }

        /**
         * Build the immutable Field instance with the parameters set. Note that key
         * parameter is mandatory.
         *
         * @return Field: immutable Field instance built
         */
        public Field build() {
            return new Field(key, highLevelType, displayName, description, containerType, isPrimaryKey);
        }

    }

    /**
     * returns the key of the Field
     * 
     * @return key.
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * returns the high Level Type of the Field
     * 
     * @return highLevelType.
     *
     */
    public String getHighLevelType() {
        return highLevelType;
    }

    /**
     * returns the display name of the Field
     * 
     * @return displayName.
     *
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * returns the description of the Field
     * 
     * @return description.
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the container type of the Field
     * 
     * @return containerType.
     *
     */
    public ContainerType getContainerType() {
        return containerType;
    }

    /**
     * returns true if the key of the Field is a primary key
     * 
     * @return isPrimaryKey.
     *
     */
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String("key", key));
        toPrint.append(line2String("highLevelType", highLevelType));
        toPrint.append(line2String("displayName", displayName));
        toPrint.append(line2String("containerType", containerType.toString()));
        toPrint.append(line2String("isPrimaryKey", isPrimaryKey));
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
