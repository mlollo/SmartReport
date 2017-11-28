package fr.inria.phoenix.diasuite.framework.context.reportcontext;

import java.io.Serializable;

/**
 * An object to store a value published by the context <code>ReportContext</code>.
 *
 * <pre>
 * context ReportContext as SensorReport[] {
 * 	when provided TriggerContext
 * 	   		get contact from ContactSensor,
 * 	   		consumption from ElectricMeter,
 * 	   		on from Light
 * 	maybe publish;
 * }
 * </pre>
 */
public final class ReportContextValue implements Serializable {
    private static final long serialVersionUID = 0;
    
    private java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value;
    
    /**
     * Get the value of the context <code>ReportContext</code>
     * 
     * @return the value of the context <code>ReportContext</code>
     */
    public java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value() {
        return value;
    }
    
    public ReportContextValue(java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value) {
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
        ReportContextValue other = (ReportContextValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
