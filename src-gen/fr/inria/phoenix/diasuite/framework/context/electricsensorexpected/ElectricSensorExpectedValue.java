package fr.inria.phoenix.diasuite.framework.context.electricsensorexpected;

import java.io.Serializable;

/**
 * An object to store a value published by the context <code>ElectricSensorExpected</code>.
 *
 * <pre>
 * context ElectricSensorExpected as SensorReport indexed by id as Integer {
 * 	when provided CheckExit 
 * 	   		get consumption from ElectricMeter
 * 	maybe publish;
 * }
 * </pre>
 */
public final class ElectricSensorExpectedValue implements Serializable {
    private static final long serialVersionUID = 0;
    
    private fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value;
    
    /**
     * Get the value of the context <code>ElectricSensorExpected</code>
     * 
     * @return the value of the context <code>ElectricSensorExpected</code>
     */
    public fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value() {
        return value;
    }
    
    private ElectricSensorExpectedIndices indices;
    
    /**
     * Get the value of the indices of the context <code>ElectricSensorExpected</code>
     * 
     * @return the value of the indices
     */
    public ElectricSensorExpectedIndices indices() {
        return indices;
    }
    
    public ElectricSensorExpectedValue(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value, ElectricSensorExpectedIndices indices) {
        this.value = value;
        this.indices = indices;
    }
    
    public ElectricSensorExpectedValue(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value,
            java.lang.Integer id) {
        this.value = value;
        this.indices = new ElectricSensorExpectedIndices(id);
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
        ElectricSensorExpectedValue other = (ElectricSensorExpectedValue) obj;
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
