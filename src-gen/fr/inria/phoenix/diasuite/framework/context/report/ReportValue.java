package fr.inria.phoenix.diasuite.framework.context.report;

import java.io.Serializable;

/**
 * An object to store a value published by the context <code>Report</code>.
 *
 * <pre>
 * context Report as SensorReport[] {
 * 	when provided ContactSensorExpected 
 * 	always publish;
 * 	when provided ElectricSensorExpected 
 * 	always publish;
 * }
 * </pre>
 */
public final class ReportValue implements Serializable {
    private static final long serialVersionUID = 0;
    
    private java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value;
    
    /**
     * Get the value of the context <code>Report</code>
     * 
     * @return the value of the context <code>Report</code>
     */
    public java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value() {
        return value;
    }
    
    public ReportValue(java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value) {
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        ReportValue other = (ReportValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
