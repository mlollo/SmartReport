package fr.inria.phoenix.diasuite.framework.device.activitynotifier;

import java.io.Serializable;

import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

/**
 * An object to store a value published on the source <code>isAlive</code> of the device <code>ActivityNotifier</code>.
 *
 * <pre>
 * device ActivityNotifier extends SoftwareSensor {
 * 	source dailyActivity as DailyActivity;
 * 	source periodActivity as PeriodActivity;
 * 	action NotifyActivity;
 * }
 * </pre>
 */
public final class IsAliveFromActivityNotifier implements Serializable {
    private static final long serialVersionUID = 0;
    
    private java.lang.Boolean value;
    
    /**
     * Get the value of the source <code>isAlive</code>
     * 
     * @return the value of the source <code>isAlive</code>
     */
    public java.lang.Boolean value() {
        return value;
    }
    
    public IsAliveFromActivityNotifier(Service service, RemoteServiceInfo remoteServiceInfo, java.lang.Boolean value) {
        this.sender = new ActivityNotifierProxy(service, remoteServiceInfo);
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
        IsAliveFromActivityNotifier other = (IsAliveFromActivityNotifier) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
    // Proxy to the sender, i.e., the device that published the source
    private ActivityNotifierProxy sender;
    
    /**
     * Get the sender of the source <code>isAlive</code>. I.e., the <code>ActivityNotifier</code> device that published the source.
     * 
     * @return A proxy to the <code>ActivityNotifier</code> that triggered the source
     */
    public ActivityNotifierProxy sender() {
        return sender;
    }
    
    /**
     * A proxy to a <code>ActivityNotifier</code> that discloses subscription/unsubscription methods.
     */
    public class ActivityNotifierProxy extends Proxy {
        private ActivityNotifierProxy(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Subcribes to publication of source <code>isAlive</code> from this <code>ActivityNotifier</code>.
         */
        public void subscribeIsAlive() {
            getService().getProcessor().subscribeValue(getService().getOutProperties(), new fr.inria.diagen.core.service.filter.SubscriptionFilter(this.getRemoteServiceInfo()), "isAlive");
        }
        
        /**
         * Unsubcribes from publication of source <code>isAlive</code> from this <code>ActivityNotifier</code>.
         */
        public void unsubscribeIsAlive() {
            getService().getProcessor().unsubscribeValue(getService().getOutProperties(), new fr.inria.diagen.core.service.filter.SubscriptionFilter(this.getRemoteServiceInfo()), "isAlive");
        }
        
        /**
         * @return the value of the <code>id</code> attribute
         */
        public java.lang.String id() {
            return (java.lang.String) callGetValue("id");
        }
    }
}
