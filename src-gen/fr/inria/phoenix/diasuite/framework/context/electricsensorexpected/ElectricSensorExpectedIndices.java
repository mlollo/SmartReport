package fr.inria.phoenix.diasuite.framework.context.electricsensorexpected;

import java.io.Serializable;

/**
 * Container for the indices of the context <code>ElectricSensorExpected</code>.
 * 
 * <pre>
 * context ElectricSensorExpected as SensorReport indexed by id as Integer {
 * 	when provided CheckExit 
 * 	   		get consumption from ElectricMeter
 * 	maybe publish;
 * }
 * </pre>
 */
public final class ElectricSensorExpectedIndices implements Serializable {
    private static final long serialVersionUID = 0;
    
    public ElectricSensorExpectedIndices(java.lang.Integer id) {
        this._id = id;
    }
    
    // Code for index id
    private java.lang.Integer _id;
    
    /**
     * Get the <code>id</code> index
     * 
     * @return the value of the <code>id</code> index
     */
    public java.lang.Integer id() {
        return _id;
    }
    
    /**
     * Set the <code>id</code> index
     * 
     * @param newId the new value for the <code>id</code> index
     */
    public void setId(java.lang.Integer newId) {
        this._id = newId;
    }
    // End of code for index id
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_id == null) ? 0 : _id.hashCode());
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
        ElectricSensorExpectedIndices other = (ElectricSensorExpectedIndices) obj;
        if (_id == null) {
            if (other._id != null)
                return false;
        } else if (!_id.equals(other._id))
            return false;
        return true;
    }
}
