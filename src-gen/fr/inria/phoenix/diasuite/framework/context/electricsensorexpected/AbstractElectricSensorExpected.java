package fr.inria.phoenix.diasuite.framework.context.electricsensorexpected;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.context.checkexit.CheckExitValue;

/**
 * <pre>
 * context ElectricSensorExpected as SensorReport indexed by id as Integer {
 * 	when provided CheckExit 
 * 	   		get consumption from ElectricMeter
 * 	maybe publish;
 * }
 * </pre>
 */
@SuppressWarnings("all")
public abstract class AbstractElectricSensorExpected extends Service {
    
    public AbstractElectricSensorExpected(ServiceConfiguration serviceConfiguration) {
        super("/Context/ElectricSensorExpected/", serviceConfiguration);
    }
    
    // Methods from the Service class
    @Override
    protected final void internalPostInitialize() {
        subscribeValue("checkExit", "/Context/CheckExit/"); // subscribe to CheckExit context
        postInitialize();
    }
    
    @Override
    public final void valueReceived(java.util.Map<String, Object> properties, RemoteServiceInfo source, String eventName, Object value, Object... indexes) {
        if (eventName.equals("checkExit") && source.isCompatible("/Context/CheckExit/")) {
            CheckExitValue checkExitValue = new CheckExitValue((java.lang.Boolean) value);
            
            ElectricSensorExpectedValuePublishable returnValue = onCheckExit(checkExitValue, new DiscoverForCheckExit());
            if(returnValue != null && returnValue.doPublish()) {
                setElectricSensorExpected(returnValue.getValue(), returnValue.getId());
            }
        }
    }
    
    @Override
    public final Object getValueCalled(java.util.Map<String, Object> properties, RemoteServiceInfo source, String valueName,
            Object... indexes) throws Exception {
        if (valueName.equals("electricSensorExpected")) {
            return getLastValue((java.lang.Integer) indexes[0]);
        }
        throw new InvocationException("Unsupported method call: " + valueName);
    }
    // End of methods from the Service class
    
    // Code relative to the return value of the context
    private java.util.Map<ElectricSensorExpectedIndices, fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> contextValues = new java.util.HashMap<ElectricSensorExpectedIndices, fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport>();
    
    private void setElectricSensorExpected(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport newContextValue, java.lang.Integer id) {
        ElectricSensorExpectedIndices indices = new ElectricSensorExpectedIndices(id);
        contextValues.put(indices, newContextValue);
        getProcessor().publishValue(getOutProperties(), "electricSensorExpected", newContextValue, id);
    }
    
    /**
     * Get the last value of the context
     * 
     * @param id the index <code>id</code> for the last value
     * @return the latest value published by the context for the given indexes
     */
    protected final fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport getLastValue(java.lang.Integer id) {
        ElectricSensorExpectedIndices indices = new ElectricSensorExpectedIndices(id);
        return contextValues.get(indices);
    }
    
    /**
     * A class that represents a value that might be published for the <code>ElectricSensorExpected</code> context. It is used by
     * event methods that might or might not publish values for this context.
     */
    protected final static class ElectricSensorExpectedValuePublishable {
        
        // The value of the context
        private fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value;
        // The id index
        private java.lang.Integer id;
        // Whether the value should be published or not
        private boolean doPublish;
        
        public ElectricSensorExpectedValuePublishable(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value,
                java.lang.Integer id,
                boolean doPublish) {
            this.value = value;
            this.id = id;
            this.doPublish = doPublish;
        }
        
        /**
         * @return the value of the context that might be published
         */
        public fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport getValue() {
            return value;
        }
        
        /**
         * Sets the value that might be published
         * 
         * @param value the value that will be published if {@link #doPublish()} returns true
         */
        public void setValue(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value) {
            this.value = value;
        }
        
        /**
         * @return the <code>id</code> index for the value of the context that might be published
         */
        public java.lang.Integer getId() {
            return id;
        }
        
        /**
         * Sets the <code>id</code> index for the value that might be published
         * 
         * @param id the <code>id</code> index
         */
        public void setId(java.lang.Integer id) {
            this.id = id;
        }
        
        /**
         * @return true if the value should be published
         */
        public boolean doPublish() {
            return doPublish;
        }
        
        /**
         * Set the value to be publishable or not
         * 
         * @param doPublish if true, the value will be published
         */
        public void setDoPublish(boolean doPublish) {
            this.doPublish = doPublish;
        }
    }
    // End of code relative to the return value of the context
    
    // Interaction contract implementation
    /**
     * This method is called when the <code>CheckExit</code> context publishes a value.
     * 
     * <pre>
     * when provided CheckExit 
     * 	   		get consumption from ElectricMeter
     * 	maybe publish;
     * </pre>
     * 
     * @param checkExitValue the value of the <code>CheckExit</code> context.
     * @param discover a discover object to get value from devices and contexts
     * @return a {@link ElectricSensorExpectedValuePublishable} that says if the context should publish a value and which value it should publish
     */
    protected abstract ElectricSensorExpectedValuePublishable onCheckExit(CheckExitValue checkExitValue, DiscoverForCheckExit discover);
    
    // End of interaction contract implementation
    
    // Discover object for CheckExit
    /**
     * An object to discover devices and contexts for the following interaction contract:
     * 
     * <code>
     * when provided CheckExit 
     * 	   		get consumption from ElectricMeter
     * 	maybe publish;
     * </code>
     */
    protected final class DiscoverForCheckExit {
        private final ElectricMeterDiscovererForCheckExit electricMeterDiscoverer = new ElectricMeterDiscovererForCheckExit(AbstractElectricSensorExpected.this);
        
        /**
         * @return a {@link ElectricMeterDiscovererForCheckExit} object to discover <code>ElectricMeter</code> devices
         */
        public ElectricMeterDiscovererForCheckExit electricMeters() {
            return electricMeterDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>ElectricMeter</code> devices to get their sources for the
     * <code>when provided CheckExit</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterDiscovererForCheckExit {
        private Service serviceParent;
        
        private ElectricMeterDiscovererForCheckExit(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private ElectricMeterCompositeForCheckExit instantiateComposite() {
            return new ElectricMeterCompositeForCheckExit(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices
         * 
         * @return a {@link ElectricMeterCompositeForCheckExit} object composed of all discoverable <code>ElectricMeter</code>
         */
        public ElectricMeterCompositeForCheckExit all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>ElectricMeter</code> devices
         * 
         * @return a {@link ElectricMeterProxyForCheckExit} object pointing to a random discoverable <code>ElectricMeter</code> device
         */
        public ElectricMeterProxyForCheckExit anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForCheckExit} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForCheckExit whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForCheckExit} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForCheckExit whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForCheckExit} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForCheckExit whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>ElectricMeter</code> devices to get their sources for the
     * <code>when provided CheckExit</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterCompositeForCheckExit extends fr.inria.diagen.core.service.composite.Composite<ElectricMeterProxyForCheckExit> {
        private ElectricMeterCompositeForCheckExit(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Sensor/EnergyMeter/ElectricMeter/");
        }
        
        @Override
        protected ElectricMeterProxyForCheckExit instantiateProxy(RemoteServiceInfo rsi) {
            return new ElectricMeterProxyForCheckExit(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForCheckExit}, filtered using the attribute <code>id</code>.
         */
        public ElectricMeterCompositeForCheckExit andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForCheckExit}, filtered using the attribute <code>location</code>.
         */
        public ElectricMeterCompositeForCheckExit andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForCheckExit}, filtered using the attribute <code>user</code>.
         */
        public ElectricMeterCompositeForCheckExit andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>ElectricMeter</code> device to get its sources for the
     * <code>when provided CheckExit</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterProxyForCheckExit extends Proxy {
        private ElectricMeterProxyForCheckExit(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Returns the value of the <code>consumption</code> source of this <code>ElectricMeter</code> device
         * 
         * @return the value of the <code>consumption</code> source
         */
        public java.lang.Float getConsumption() throws InvocationException {
            return (java.lang.Float) callGetValue("consumption");
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
    // End of discover object for CheckExit
}
