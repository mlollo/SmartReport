package fr.inria.phoenix.diasuite.framework.device.lightsensor;

import java.io.Serializable;

import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

/**
 * An object to store a value published on the source <code>lightLevel</code> of the device <code>LightSensor</code>.
 *
 * <pre>
 * device LightSensor extends Sensor {
 * 	source lightLevel as Float;
 * }
 * </pre>
 */
public final class LightLevelFromLightSensor implements Serializable {
    private static final long serialVersionUID = 0;
    
    private java.lang.Float value;
    
    /**
     * Get the value of the source <code>lightLevel</code>
     * 
     * @return the value of the source <code>lightLevel</code>
     */
    public java.lang.Float value() {
        return value;
    }
    
    public LightLevelFromLightSensor(Service service, RemoteServiceInfo remoteServiceInfo, java.lang.Float value) {
        this.sender = new LightSensorProxy(service, remoteServiceInfo);
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
        LightLevelFromLightSensor other = (LightLevelFromLightSensor) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
    // Proxy to the sender, i.e., the device that published the source
    private LightSensorProxy sender;
    
    /**
     * Get the sender of the source <code>lightLevel</code>. I.e., the <code>LightSensor</code> device that published the source.
     * 
     * @return A proxy to the <code>LightSensor</code> that triggered the source
     */
    public LightSensorProxy sender() {
        return sender;
    }
    
    /**
     * A proxy to a <code>LightSensor</code> that discloses subscription/unsubscription methods.
     */
    public class LightSensorProxy extends Proxy {
        private LightSensorProxy(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Subcribes to publication of source <code>lightLevel</code> from this <code>LightSensor</code>.
         */
        public void subscribeLightLevel() {
            getService().getProcessor().subscribeValue(getService().getOutProperties(), new fr.inria.diagen.core.service.filter.SubscriptionFilter(this.getRemoteServiceInfo()), "lightLevel");
        }
        
        /**
         * Unsubcribes from publication of source <code>lightLevel</code> from this <code>LightSensor</code>.
         */
        public void unsubscribeLightLevel() {
            getService().getProcessor().unsubscribeValue(getService().getOutProperties(), new fr.inria.diagen.core.service.filter.SubscriptionFilter(this.getRemoteServiceInfo()), "lightLevel");
        }
        
        /**
         * @return the value of the <code>id</code> attribute
         */
        public java.lang.String id() {
            return (java.lang.String) callGetValue("id");
        }
        
        /**
         * @return the value of the <code>location</code> attribute
         */
        public java.lang.String location() {
            return (java.lang.String) callGetValue("location");
        }
        
        /**
         * @return the value of the <code>user</code> attribute
         */
        public java.lang.String user() {
            return (java.lang.String) callGetValue("user");
        }
    }
}
