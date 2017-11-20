package fr.inria.phoenix.diasuite.framework.mocks;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.device.electricmeter.AbstractElectricMeter;

// @internal
public final class ElectricMeterMock {
    
    /**
     * The timeout in milliseconds to wait action. Set it if your application computations are long.
     */
    public long TIMEOUT = Mock.TIMEOUT;
    
    class Proxy extends AbstractElectricMeter {
        private Proxy(ServiceConfiguration serviceConfiguration,
                java.lang.String idValue,
                java.lang.String locationValue,
                java.lang.String userValue) {
            super(serviceConfiguration, idValue, locationValue, userValue);
        }
        
        public void _updateId(java.lang.String newIdValue) {
            updateId(newIdValue);
        }
        
        public void _updateLocation(java.lang.String newLocationValue) {
            updateLocation(newLocationValue);
        }
        
        public void _updateUser(java.lang.String newUserValue) {
            updateUser(newUserValue);
        }
        
        protected java.lang.Float getConsumption() throws Exception {
            java.lang.Float result = __consumption;
            if(result == null)
                return super.getConsumption();
            return result;
        }
        
        public void _publishConsumption(java.lang.Float newConsumptionValue) {
            publishConsumption(newConsumptionValue);
        }
        
        protected java.lang.Boolean getIsAlive() throws Exception {
            java.lang.Boolean result = __isAlive;
            if(result == null)
                return super.getIsAlive();
            return result;
        }
        
        public void _publishIsAlive(java.lang.Boolean newIsAliveValue) {
            publishIsAlive(newIsAliveValue);
        }
    }
    
    Proxy proxy;
    
    ElectricMeterMock(ServiceConfiguration serviceConfiguration,
            java.lang.String idValue,
            java.lang.String locationValue,
            java.lang.String userValue) {
        this.proxy = new Proxy(serviceConfiguration, idValue, locationValue, userValue);
    }
    
    
    // Code for the attribute id from device Device
    /**
     * Set the value of the <code>id</code> attribute from device <code>Device</code>.
     * 
     * <pre>
     * attribute id as String;
     * </pre>
     * @param newIdValue the new value of <code>id</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock id(java.lang.String newIdValue) {
        proxy._updateId(newIdValue);
        return this;
    }
    // End of code for the attribute id from device Device
    
    // Code for the attribute location from device PhysicalDevice
    /**
     * Set the value of the <code>location</code> attribute from device <code>PhysicalDevice</code>.
     * 
     * <pre>
     * attribute location as String;
     * </pre>
     * @param newLocationValue the new value of <code>location</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock location(java.lang.String newLocationValue) {
        proxy._updateLocation(newLocationValue);
        return this;
    }
    // End of code for the attribute location from device PhysicalDevice
    
    // Code for the attribute user from device PhysicalDevice
    /**
     * Set the value of the <code>user</code> attribute from device <code>PhysicalDevice</code>.
     * 
     * <pre>
     * attribute user as String;
     * </pre>
     * @param newUserValue the new value of <code>user</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock user(java.lang.String newUserValue) {
        proxy._updateUser(newUserValue);
        return this;
    }
    // End of code for the attribute user from device PhysicalDevice
    
    // Code for source consumption from device ElectricMeter
    /**
     * Publish the value of source <code>consumption</code> from device <code>ElectricMeter</code>.
     * 
     * <pre>
     * source consumption as Float;
     * </pre>
     * @param newConsumptionValue the new value for the source <code>consumption</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock consumption(java.lang.Float newConsumptionValue) {
        proxy._publishConsumption(newConsumptionValue);
        return this;
    }
    
    private java.lang.Float __consumption = null;
    /**
     * Set the value (without publication) of source <code>consumption</code> from device <code>ElectricMeter</code>.
     * 
     * <pre>
     * source consumption as Float;
     * </pre>
     * @param newConsumptionValue the new value for the source <code>consumption</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock setConsumption(java.lang.Float newConsumptionValue) {
        __consumption = newConsumptionValue;
        return this;
    }
    // End of code for source consumption from device ElectricMeter
    
    // Code for source isAlive from device Device
    /**
     * Publish the value of source <code>isAlive</code> from device <code>Device</code>.
     * 
     * <pre>
     * source isAlive as Boolean;
     * </pre>
     * @param newIsAliveValue the new value for the source <code>isAlive</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock isAlive(java.lang.Boolean newIsAliveValue) {
        proxy._publishIsAlive(newIsAliveValue);
        return this;
    }
    
    private java.lang.Boolean __isAlive = null;
    /**
     * Set the value (without publication) of source <code>isAlive</code> from device <code>Device</code>.
     * 
     * <pre>
     * source isAlive as Boolean;
     * </pre>
     * @param newIsAliveValue the new value for the source <code>isAlive</code>
     * @return this for fluent interface
     */
    public ElectricMeterMock setIsAlive(java.lang.Boolean newIsAliveValue) {
        __isAlive = newIsAliveValue;
        return this;
    }
    // End of code for source isAlive from device Device
}
