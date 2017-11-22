package fr.inria.phoenix.diasuite.framework.context.reportcontext;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.device.inactivitysensor.InactivityLevelFromInactivitySensor;

/**
 * <pre>
 * context ReportContext as SensorReport[] {
 * 	when provided inactivityLevel from InactivitySensor
 * 	   		get contact from ContactSensor,
 * 	   		consumption from ElectricMeter
 * 	maybe publish;
 * }
 * </pre>
 */
@SuppressWarnings("all")
public abstract class AbstractReportContext extends Service {
    
    public AbstractReportContext(ServiceConfiguration serviceConfiguration) {
        super("/Context/ReportContext/", serviceConfiguration);
    }
    
    // Methods from the Service class
    @Override
    protected final void internalPostInitialize() {
        postInitialize();
    }
    
    @Override
    protected void postInitialize() {
        // Default implementation of post initialize: subscribe to all required devices
        discoverInactivitySensorForSubscribe.all().subscribeInactivityLevel(); // subscribe to inactivityLevel from all InactivitySensor devices
    }
    
    @Override
    public final void valueReceived(java.util.Map<String, Object> properties, RemoteServiceInfo source, String eventName, Object value, Object... indexes) {
        if (eventName.equals("inactivityLevel") && source.isCompatible("/Device/Device/Service/SoftwareSensor/InactivitySensor/")) {
            InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor = new InactivityLevelFromInactivitySensor(this, source, (java.lang.Float) value);
            
            ReportContextValuePublishable returnValue = onInactivityLevelFromInactivitySensor(inactivityLevelFromInactivitySensor, new DiscoverForInactivityLevelFromInactivitySensor());
            if(returnValue != null && returnValue.doPublish()) {
                setReportContext(returnValue.getValue());
            }
        }
    }
    
    @Override
    public final Object getValueCalled(java.util.Map<String, Object> properties, RemoteServiceInfo source, String valueName,
            Object... indexes) throws Exception {
        if (valueName.equals("reportContext")) {
            return getLastValue();
        }
        throw new InvocationException("Unsupported method call: " + valueName);
    }
    // End of methods from the Service class
    
    // Code relative to the return value of the context
    private java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> contextValue;
    
    private void setReportContext(java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> newContextValue) {
        contextValue = newContextValue;
        getProcessor().publishValue(getOutProperties(), "reportContext", newContextValue);
    }
    
    /**
     * Get the last value of the context
     * 
     * @return the latest value published by the context
     */
    protected final java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> getLastValue() {
        return contextValue;
    }
    
    /**
     * A class that represents a value that might be published for the <code>ReportContext</code> context. It is used by
     * event methods that might or might not publish values for this context.
     */
    protected final static class ReportContextValuePublishable {
        
        // The value of the context
        private java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value;
        // Whether the value should be published or not
        private boolean doPublish;
        
        public ReportContextValuePublishable(java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value, boolean doPublish) {
            this.value = value;
            this.doPublish = doPublish;
        }
        
        /**
         * @return the value of the context that might be published
         */
        public java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> getValue() {
            return value;
        }
        
        /**
         * Sets the value that might be published
         * 
         * @param value the value that will be published if {@link #doPublish()} returns true
         */
        public void setValue(java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> value) {
            this.value = value;
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
     * This method is called when a <code>InactivitySensor</code> device on which we have subscribed publish on its <code>inactivityLevel</code> source.
     * 
     * <pre>
     * when provided inactivityLevel from InactivitySensor
     * 	   		get contact from ContactSensor,
     * 	   		consumption from ElectricMeter
     * 	maybe publish;
     * </pre>
     * 
     * @param inactivityLevelFromInactivitySensor the value of the <code>inactivityLevel</code> source and the <code>InactivitySensor</code> device that published the value.
     * @param discover a discover object to get value from devices and contexts
     * @return a {@link ReportContextValuePublishable} that says if the context should publish a value and which value it should publish
     */
    protected abstract ReportContextValuePublishable onInactivityLevelFromInactivitySensor(InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor, DiscoverForInactivityLevelFromInactivitySensor discover);
    
    // End of interaction contract implementation
    
    // Discover part for InactivitySensor devices
    /**
     * Use this object to discover InactivitySensor devices.
     * <p>
     * ------------------------------------------------------------
     * Presence Detector					||
     * ------------------------------------------------------------
     * enumeration Room {
     * ENTRANCE, BEDROOM, KITCHEN, BATHROOM, LIVING, TOILET
     * }
     * 
     * device IndoorLocationDetector extends SoftwareSensor {
     * // AR (14/02/17)
     * source currentRoom as Room;
     * action SetLocation;
     * }
     * 
     * action SetLocation {
     * setLocation(location as Room);
     * resetLocation();
     * }
     * ------------------------------------------------------------
     * InactivitySensor					||
     * ------------------------------------------------------------
     * AR (01/08/17)
     * 
     * <pre>
     * device InactivitySensor extends SoftwareSensor {
     * 	source inactivityLevel as Float;
     * 	source lastInteraction as Interaction;
     * 	action UpdateInactivityLevel;
     * }
     * </pre>
     * 
     * @see InactivitySensorDiscoverer
     */
    protected final InactivitySensorDiscoverer discoverInactivitySensorForSubscribe = new InactivitySensorDiscoverer(this);
    
    /**
     * Discover object that will exposes the <code>InactivitySensor</code> devices that can be discovered
     * <p>
     * ------------------------------------------------------------
     * Presence Detector					||
     * ------------------------------------------------------------
     * enumeration Room {
     * ENTRANCE, BEDROOM, KITCHEN, BATHROOM, LIVING, TOILET
     * }
     * 
     * device IndoorLocationDetector extends SoftwareSensor {
     * // AR (14/02/17)
     * source currentRoom as Room;
     * action SetLocation;
     * }
     * 
     * action SetLocation {
     * setLocation(location as Room);
     * resetLocation();
     * }
     * ------------------------------------------------------------
     * InactivitySensor					||
     * ------------------------------------------------------------
     * AR (01/08/17)
     * 
     * <pre>
     * device InactivitySensor extends SoftwareSensor {
     * 	source inactivityLevel as Float;
     * 	source lastInteraction as Interaction;
     * 	action UpdateInactivityLevel;
     * }
     * </pre>
     */
    protected final static class InactivitySensorDiscoverer {
        private Service serviceParent;
        
        private InactivitySensorDiscoverer(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private InactivitySensorComposite instantiateComposite() {
            return new InactivitySensorComposite(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>InactivitySensor</code> devices
         * 
         * @return a {@link InactivitySensorComposite} object composed of all discoverable <code>InactivitySensor</code>
         */
        public InactivitySensorComposite all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>InactivitySensor</code> devices
         * 
         * @return a {@link InactivitySensorProxy} object pointing to a random discoverable <code>InactivitySensor</code> device
         */
        public InactivitySensorProxy anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>InactivitySensor</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link InactivitySensorComposite} object composed of all matching <code>InactivitySensor</code> devices
         */
        public InactivitySensorComposite whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
    }
    
    /**
     * A composite of several <code>InactivitySensor</code> devices
     * <p>
     * ------------------------------------------------------------
     * Presence Detector					||
     * ------------------------------------------------------------
     * enumeration Room {
     * ENTRANCE, BEDROOM, KITCHEN, BATHROOM, LIVING, TOILET
     * }
     * 
     * device IndoorLocationDetector extends SoftwareSensor {
     * // AR (14/02/17)
     * source currentRoom as Room;
     * action SetLocation;
     * }
     * 
     * action SetLocation {
     * setLocation(location as Room);
     * resetLocation();
     * }
     * ------------------------------------------------------------
     * InactivitySensor					||
     * ------------------------------------------------------------
     * AR (01/08/17)
     * 
     * <pre>
     * device InactivitySensor extends SoftwareSensor {
     * 	source inactivityLevel as Float;
     * 	source lastInteraction as Interaction;
     * 	action UpdateInactivityLevel;
     * }
     * </pre>
     */
    protected final static class InactivitySensorComposite extends fr.inria.diagen.core.service.composite.Composite<InactivitySensorProxy> {
        private InactivitySensorComposite(Service serviceParent) {
            super(serviceParent, "/Device/Device/Service/SoftwareSensor/InactivitySensor/");
        }
        
        @Override
        protected InactivitySensorProxy instantiateProxy(RemoteServiceInfo rsi) {
            return new InactivitySensorProxy(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link InactivitySensorComposite}, filtered using the attribute <code>id</code>.
         */
        public InactivitySensorComposite andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Subscribes to the <code>inactivityLevel</code> source. After a call to this method, the context will be notified when a
         * <code>InactivitySensor</code> device of this composite publishes a value on its <code>inactivityLevel</code> source.
         */
        public void subscribeInactivityLevel() {
            subscribeValue("inactivityLevel");
        }
        
        /**
         * Unsubscribes from the <code>inactivityLevel</code> source. After a call to this method, the context will no more be notified
         * when a <code>InactivitySensor</code> device of this composite publishes a value on its <code>inactivityLevel</code> source.
         */
        public void unsubscribeInactivityLevel() {
            unsubscribeValue("inactivityLevel");
        }
    }
    
    /**
     * A proxy to one <code>InactivitySensor</code> device
     * <p>
     * ------------------------------------------------------------
     * Presence Detector					||
     * ------------------------------------------------------------
     * enumeration Room {
     * ENTRANCE, BEDROOM, KITCHEN, BATHROOM, LIVING, TOILET
     * }
     * 
     * device IndoorLocationDetector extends SoftwareSensor {
     * // AR (14/02/17)
     * source currentRoom as Room;
     * action SetLocation;
     * }
     * 
     * action SetLocation {
     * setLocation(location as Room);
     * resetLocation();
     * }
     * ------------------------------------------------------------
     * InactivitySensor					||
     * ------------------------------------------------------------
     * AR (01/08/17)
     * 
     * <pre>
     * device InactivitySensor extends SoftwareSensor {
     * 	source inactivityLevel as Float;
     * 	source lastInteraction as Interaction;
     * 	action UpdateInactivityLevel;
     * }
     * </pre>
     */
    protected final static class InactivitySensorProxy extends Proxy {
        private InactivitySensorProxy(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Subscribes to the <code>inactivityLevel</code> source. After a call to this method, the context will be notified when the
         * <code>InactivitySensor</code> device of this proxy publishes a value on its <code>inactivityLevel</code> source.
         */
        public void subscribeInactivityLevel() {
            subscribeValue("inactivityLevel");
        }
        
        /**
         * Unsubscribes from the <code>inactivityLevel</code> source. After a call to this method, the context will no more be notified
         * when the <code>InactivitySensor</code> device of this proxy publishes a value on its <code>inactivityLevel</code> source.
         */
        public void unsubscribeInactivityLevel() {
            unsubscribeValue("inactivityLevel");
        }
        
        /**
         * @return the value of the <code>id</code> attribute
         */
        public java.lang.String id() {
            return (java.lang.String) callGetValue("id");
        }
    }
    // End of discover part for InactivitySensor devices
    
    // Discover object for inactivityLevel from InactivitySensor
    /**
     * An object to discover devices and contexts for the following interaction contract:
     * 
     * <code>
     * when provided inactivityLevel from InactivitySensor
     * 	   		get contact from ContactSensor,
     * 	   		consumption from ElectricMeter
     * 	maybe publish;
     * </code>
     */
    protected final class DiscoverForInactivityLevelFromInactivitySensor {
        private final ContactSensorDiscovererForInactivityLevelFromInactivitySensor contactSensorDiscoverer = new ContactSensorDiscovererForInactivityLevelFromInactivitySensor(AbstractReportContext.this);
        private final ElectricMeterDiscovererForInactivityLevelFromInactivitySensor electricMeterDiscoverer = new ElectricMeterDiscovererForInactivityLevelFromInactivitySensor(AbstractReportContext.this);
        
        /**
         * @return a {@link ContactSensorDiscovererForInactivityLevelFromInactivitySensor} object to discover <code>ContactSensor</code> devices
         */
        public ContactSensorDiscovererForInactivityLevelFromInactivitySensor contactSensors() {
            return contactSensorDiscoverer;
        }
        
        /**
         * @return a {@link ElectricMeterDiscovererForInactivityLevelFromInactivitySensor} object to discover <code>ElectricMeter</code> devices
         */
        public ElectricMeterDiscovererForInactivityLevelFromInactivitySensor electricMeters() {
            return electricMeterDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>ContactSensor</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorDiscovererForInactivityLevelFromInactivitySensor {
        private Service serviceParent;
        
        private ContactSensorDiscovererForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private ContactSensorCompositeForInactivityLevelFromInactivitySensor instantiateComposite() {
            return new ContactSensorCompositeForInactivityLevelFromInactivitySensor(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices
         * 
         * @return a {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor} object composed of all discoverable <code>ContactSensor</code>
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>ContactSensor</code> devices
         * 
         * @return a {@link ContactSensorProxyForInactivityLevelFromInactivitySensor} object pointing to a random discoverable <code>ContactSensor</code> device
         */
        public ContactSensorProxyForInactivityLevelFromInactivitySensor anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>ContactSensor</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorCompositeForInactivityLevelFromInactivitySensor extends fr.inria.diagen.core.service.composite.Composite<ContactSensorProxyForInactivityLevelFromInactivitySensor> {
        private ContactSensorCompositeForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Sensor/ContactSensor/");
        }
        
        @Override
        protected ContactSensorProxyForInactivityLevelFromInactivitySensor instantiateProxy(RemoteServiceInfo rsi) {
            return new ContactSensorProxyForInactivityLevelFromInactivitySensor(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>id</code>.
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>location</code>.
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>user</code>.
         */
        public ContactSensorCompositeForInactivityLevelFromInactivitySensor andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>ContactSensor</code> device to get its sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorProxyForInactivityLevelFromInactivitySensor extends Proxy {
        private ContactSensorProxyForInactivityLevelFromInactivitySensor(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Returns the value of the <code>contact</code> source of this <code>ContactSensor</code> device
         * 
         * @return the value of the <code>contact</code> source
         */
        public java.lang.Boolean getContact() throws InvocationException {
            return (java.lang.Boolean) callGetValue("contact");
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
    
    /**
     * Discover object that will exposes the <code>ElectricMeter</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterDiscovererForInactivityLevelFromInactivitySensor {
        private Service serviceParent;
        
        private ElectricMeterDiscovererForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private ElectricMeterCompositeForInactivityLevelFromInactivitySensor instantiateComposite() {
            return new ElectricMeterCompositeForInactivityLevelFromInactivitySensor(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices
         * 
         * @return a {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor} object composed of all discoverable <code>ElectricMeter</code>
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>ElectricMeter</code> devices
         * 
         * @return a {@link ElectricMeterProxyForInactivityLevelFromInactivitySensor} object pointing to a random discoverable <code>ElectricMeter</code> device
         */
        public ElectricMeterProxyForInactivityLevelFromInactivitySensor anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>ElectricMeter</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterCompositeForInactivityLevelFromInactivitySensor extends fr.inria.diagen.core.service.composite.Composite<ElectricMeterProxyForInactivityLevelFromInactivitySensor> {
        private ElectricMeterCompositeForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Sensor/EnergyMeter/ElectricMeter/");
        }
        
        @Override
        protected ElectricMeterProxyForInactivityLevelFromInactivitySensor instantiateProxy(RemoteServiceInfo rsi) {
            return new ElectricMeterProxyForInactivityLevelFromInactivitySensor(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>id</code>.
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>location</code>.
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>user</code>.
         */
        public ElectricMeterCompositeForInactivityLevelFromInactivitySensor andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>ElectricMeter</code> device to get its sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterProxyForInactivityLevelFromInactivitySensor extends Proxy {
        private ElectricMeterProxyForInactivityLevelFromInactivitySensor(Service service, RemoteServiceInfo remoteServiceInfo) {
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
    // End of discover object for inactivityLevel from InactivitySensor
}
