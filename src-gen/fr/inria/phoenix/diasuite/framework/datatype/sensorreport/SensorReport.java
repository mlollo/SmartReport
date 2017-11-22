package fr.inria.phoenix.diasuite.framework.datatype.sensorreport;

import java.io.Serializable;

/**
 * <pre>
 * structure SensorReport {
 * 	id as String;
 * 	name as String;
 * 	expectedValue as String;
 * 	value as String;
 * }
 * </pre>
 */
public class SensorReport implements Serializable {
    private static final long serialVersionUID = 0;

    // Code for field id
    private java.lang.String id;
    
    /**
     * Returns the value of the id field.
     * 
     * <pre>
     * id as String
     * </pre>
     * @return the value of id
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * Set the value of the id field.
     * 
     * <pre>
     * id as String
     * </pre>
     * @param id the new value of id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    // End of code for field id

    // Code for field name
    private java.lang.String name;
    
    /**
     * Returns the value of the name field.
     * 
     * <pre>
     * name as String
     * </pre>
     * @return the value of name
     */
    public java.lang.String getName() {
        return name;
    }
    
    /**
     * Set the value of the name field.
     * 
     * <pre>
     * name as String
     * </pre>
     * @param name the new value of name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    // End of code for field name

    // Code for field expectedValue
    private java.lang.String expectedValue;
    
    /**
     * Returns the value of the expectedValue field.
     * 
     * <pre>
     * expectedValue as String
     * </pre>
     * @return the value of expectedValue
     */
    public java.lang.String getExpectedValue() {
        return expectedValue;
    }
    
    /**
     * Set the value of the expectedValue field.
     * 
     * <pre>
     * expectedValue as String
     * </pre>
     * @param expectedValue the new value of expectedValue
     */
    public void setExpectedValue(java.lang.String expectedValue) {
        this.expectedValue = expectedValue;
    }
    // End of code for field expectedValue

    // Code for field value
    private java.lang.String value;
    
    /**
     * Returns the value of the value field.
     * 
     * <pre>
     * value as String
     * </pre>
     * @return the value of value
     */
    public java.lang.String getValue() {
        return value;
    }
    
    /**
     * Set the value of the value field.
     * 
     * <pre>
     * value as String
     * </pre>
     * @param value the new value of value
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }
    // End of code for field value

    public SensorReport() {
    }

    public SensorReport(java.lang.String id,
            java.lang.String name,
            java.lang.String expectedValue,
            java.lang.String value) {
        this.id = id;
        this.name = name;
        this.expectedValue = expectedValue;
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((expectedValue == null) ? 0 : expectedValue.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SensorReport other = (SensorReport) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (expectedValue == null) {
            if (other.expectedValue != null)
                return false;
        } else if (!expectedValue.equals(other.expectedValue))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SensorReport [" + 
            "id=" + id +", " + 
            "name=" + name +", " + 
            "expectedValue=" + expectedValue +", " + 
            "value=" + value +
        "]";
    }
}
