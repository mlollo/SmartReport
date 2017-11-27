package fr.inria.phoenix.diasuite.framework.context.triggercontext;

import java.io.Serializable;

/**
 * An object to store a value published by the context <code>TriggerContext</code>.
 *
 * <pre>
 * context TriggerContext as Boolean {
 * 	when provided inactivityLevel from InactivitySensor
 * 	   		get lastInteraction from InactivitySensor,
 * 	   		on from Light
 * 	maybe publish;
 * }
 * </pre>
 */
public final class TriggerContextValue implements Serializable {
    private static final long serialVersionUID = 0;
    
    private java.lang.Boolean value;
    
    /**
     * Get the value of the context <code>TriggerContext</code>
     * 
     * @return the value of the context <code>TriggerContext</code>
     */
    public java.lang.Boolean value() {
        return value;
    }
    
    public TriggerContextValue(java.lang.Boolean value) {
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
        TriggerContextValue other = (TriggerContextValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
