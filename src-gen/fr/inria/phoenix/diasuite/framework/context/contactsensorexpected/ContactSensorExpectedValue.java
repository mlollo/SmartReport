package fr.inria.phoenix.diasuite.framework.context.contactsensorexpected;

import java.io.Serializable;

/**
 * An object to store a value published by the context <code>ContactSensorExpected</code>.
 *
 * <pre>
 * context ContactSensorExpected as SensorReport indexed by id as Integer {
 * 	when provided CheckExit 
 * 	   		get contact from ContactSensor
 * 	maybe publish;
 * }
 * </pre>
 */
public final class ContactSensorExpectedValue implements Serializable {
    private static final long serialVersionUID = 0;
    
    private fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value;
    
    /**
     * Get the value of the context <code>ContactSensorExpected</code>
     * 
     * @return the value of the context <code>ContactSensorExpected</code>
     */
    public fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value() {
        return value;
    }
    
    private ContactSensorExpectedIndices indices;
    
    /**
     * Get the value of the indices of the context <code>ContactSensorExpected</code>
     * 
     * @return the value of the indices
     */
    public ContactSensorExpectedIndices indices() {
        return indices;
    }
    
    public ContactSensorExpectedValue(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value, ContactSensorExpectedIndices indices) {
        this.value = value;
        this.indices = indices;
    }
    
    public ContactSensorExpectedValue(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value,
            java.lang.Integer id) {
        this.value = value;
        this.indices = new ContactSensorExpectedIndices(id);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + ((indices == null) ? 0 : indices.hashCode());
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
        ContactSensorExpectedValue other = (ContactSensorExpectedValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        if (indices == null) {
            if (other.indices != null)
                return false;
        } else if (!indices.equals(other.indices))
            return false;
        return true;
    }
}
