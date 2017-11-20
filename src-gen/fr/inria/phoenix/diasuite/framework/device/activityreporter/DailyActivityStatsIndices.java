package fr.inria.phoenix.diasuite.framework.device.activityreporter;

import java.io.Serializable;

/**
 * Container for the indices of the source <code>dailyActivityStats</code> from device <code>ActivityReporter</code>.
 * 
 * <pre>
 * source dailyActivityStats as DailyActivityStats indexed by activity as DailyActivityName;
 * </pre>
 */
public final class DailyActivityStatsIndices implements Serializable {
    private static final long serialVersionUID = 0;
    
    public DailyActivityStatsIndices(fr.inria.phoenix.diasuite.framework.datatype.dailyactivityname.DailyActivityName activity) {
        this._activity = activity;
    }
    
    // Code for index activity
    private fr.inria.phoenix.diasuite.framework.datatype.dailyactivityname.DailyActivityName _activity;
    
    /**
     * Get the <code>activity</code> index
     * 
     * @return the value of the <code>activity</code> index
     */
    public fr.inria.phoenix.diasuite.framework.datatype.dailyactivityname.DailyActivityName activity() {
        return _activity;
    }
    
    /**
     * Set the <code>activity</code> index
     * 
     * @param newActivity the new value for the <code>activity</code> index
     */
    public void setActivity(fr.inria.phoenix.diasuite.framework.datatype.dailyactivityname.DailyActivityName newActivity) {
        this._activity = newActivity;
    }
    // End of code for index activity
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_activity == null) ? 0 : _activity.hashCode());
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
        DailyActivityStatsIndices other = (DailyActivityStatsIndices) obj;
        if (_activity == null) {
            if (other._activity != null)
                return false;
        } else if (!_activity.equals(other._activity))
            return false;
        return true;
    }
}
