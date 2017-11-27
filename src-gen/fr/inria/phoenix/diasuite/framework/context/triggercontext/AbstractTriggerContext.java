package fr.inria.phoenix.diasuite.framework.context.triggercontext;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.device.inactivitysensor.InactivityLevelFromInactivitySensor;

/**
 * <pre>
 * context TriggerContext as Boolean {
 * 	when provided inactivityLevel from InactivitySensor
 * 	   		get lastInteraction from InactivitySensor,
 * 	   		on from Light
 * 	maybe publish;
 * }
 * </pre>
 */
@SuppressWarnings("all")
public abstract class AbstractTriggerContext extends Service {
    
    public AbstractTriggerContext(ServiceConfiguration serviceConfiguration) {
        super("/Context/TriggerContext/", serviceConfiguration);
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
            
            TriggerContextValuePublishable returnValue = onInactivityLevelFromInactivitySensor(inactivityLevelFromInactivitySensor, new DiscoverForInactivityLevelFromInactivitySensor());
            if(returnValue != null && returnValue.doPublish()) {
                setTriggerContext(returnValue.getValue());
            }
        }
    }
    
    @Override
    public final Object getValueCalled(java.util.Map<String, Object> properties, RemoteServiceInfo source, String valueName,
            Object... indexes) throws Exception {
        if (valueName.equals("triggerContext")) {
            return getLastValue();
        }
        throw new InvocationException("Unsupported method call: " + valueName);
    }
    // End of methods from the Service class
    
    // Code relative to the return value of the context
    private java.lang.Boolean contextValue;
    
    private void setTriggerContext(java.lang.Boolean newContextValue) {
        contextValue = newContextValue;
        getProcessor().publishValue(getOutProperties(), "triggerContext", newContextValue);
    }
    
    /**
     * Get the last value of the context
     * 
     * @return the latest value published by the context
     */
    protected final java.lang.Boolean getLastValue() {
        return contextValue;
    }
    
    /**
     * A class that represents a value that might be published for the <code>TriggerContext</code> context. It is used by
     * event methods that might or might not publish values for this context.
     */
    protected final static class TriggerContextValuePublishable {
        
        // The value of the context
        private java.lang.Boolean value;
        // Whether the value should be published or not
        private boolean doPublish;
        
        public TriggerContextValuePublishable(java.lang.Boolean value, boolean doPublish) {
            this.value = value;
            this.doPublish = doPublish;
        }
        
        /**
         * @return the value of the context that might be published
         */
        public java.lang.Boolean getValue() {
            return value;
        }
        
        /**
         * Sets the value that might be published
         * 
         * @param value the value that will be published if {@link #doPublish()} returns true
         */
        public void setValue(java.lang.Boolean value) {
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
     * 	   		get lastInteraction from InactivitySensor,
     * 	   		on from Light
     * 	maybe publish;
     * </pre>
     * 
     * @param inactivityLevelFromInactivitySensor the value of the <code>inactivityLevel</code> source and the <code>InactivitySensor</code> device that published the value.
     * @param discover a discover object to get value from devices and contexts
     * @return a {@link TriggerContextValuePublishable} that says if the context should publish a value and which value it should publish
     */
    protected abstract TriggerContextValuePublishable onInactivityLevelFromInactivitySensor(InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor, DiscoverForInactivityLevelFromInactivitySensor discover);
    
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
     * 	   		get lastInteraction from InactivitySensor,
     * 	   		on from Light
     * 	maybe publish;
     * </code>
     */
    protected final class DiscoverForInactivityLevelFromInactivitySensor {
        private final InactivitySensorDiscovererForInactivityLevelFromInactivitySensor inactivitySensorDiscoverer = new InactivitySensorDiscovererForInactivityLevelFromInactivitySensor(AbstractTriggerContext.this);
        private final LightDiscovererForInactivityLevelFromInactivitySensor lightDiscoverer = new LightDiscovererForInactivityLevelFromInactivitySensor(AbstractTriggerContext.this);
        
        /**
         * @return a {@link InactivitySensorDiscovererForInactivityLevelFromInactivitySensor} object to discover <code>InactivitySensor</code> devices
         */
        public InactivitySensorDiscovererForInactivityLevelFromInactivitySensor inactivitySensors() {
            return inactivitySensorDiscoverer;
        }
        
        /**
         * @return a {@link LightDiscovererForInactivityLevelFromInactivitySensor} object to discover <code>Light</code> devices
         */
        public LightDiscovererForInactivityLevelFromInactivitySensor lights() {
            return lightDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>InactivitySensor</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
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
    protected final static class InactivitySensorDiscovererForInactivityLevelFromInactivitySensor {
        private Service serviceParent;
        
        private InactivitySensorDiscovererForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private InactivitySensorCompositeForInactivityLevelFromInactivitySensor instantiateComposite() {
            return new InactivitySensorCompositeForInactivityLevelFromInactivitySensor(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>InactivitySensor</code> devices
         * 
         * @return a {@link InactivitySensorCompositeForInactivityLevelFromInactivitySensor} object composed of all discoverable <code>InactivitySensor</code>
         */
        public InactivitySensorCompositeForInactivityLevelFromInactivitySensor all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>InactivitySensor</code> devices
         * 
         * @return a {@link InactivitySensorProxyForInactivityLevelFromInactivitySensor} object pointing to a random discoverable <code>InactivitySensor</code> device
         */
        public InactivitySensorProxyForInactivityLevelFromInactivitySensor anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>InactivitySensor</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link InactivitySensorCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>InactivitySensor</code> devices
         */
        public InactivitySensorCompositeForInactivityLevelFromInactivitySensor whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
    }
    
    /**
     * A composite of several <code>InactivitySensor</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
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
    protected final static class InactivitySensorCompositeForInactivityLevelFromInactivitySensor extends fr.inria.diagen.core.service.composite.Composite<InactivitySensorProxyForInactivityLevelFromInactivitySensor> {
        private InactivitySensorCompositeForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super(serviceParent, "/Device/Device/Service/SoftwareSensor/InactivitySensor/");
        }
        
        @Override
        protected InactivitySensorProxyForInactivityLevelFromInactivitySensor instantiateProxy(RemoteServiceInfo rsi) {
            return new InactivitySensorProxyForInactivityLevelFromInactivitySensor(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link InactivitySensorCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>id</code>.
         */
        public InactivitySensorCompositeForInactivityLevelFromInactivitySensor andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>InactivitySensor</code> device to get its sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
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
    protected final static class InactivitySensorProxyForInactivityLevelFromInactivitySensor extends Proxy {
        private InactivitySensorProxyForInactivityLevelFromInactivitySensor(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Returns the value of the <code>lastInteraction</code> source of this <code>InactivitySensor</code> device
         * 
         * @return the value of the <code>lastInteraction</code> source
         */
        public fr.inria.phoenix.diasuite.framework.datatype.interaction.Interaction getLastInteraction() throws InvocationException {
            return (fr.inria.phoenix.diasuite.framework.datatype.interaction.Interaction) callGetValue("lastInteraction");
        }
        
        /**
         * @return the value of the <code>id</code> attribute
         */
        public java.lang.String id() {
            return (java.lang.String) callGetValue("id");
        }
    }
    
    /**
     * Discover object that will exposes the <code>Light</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * <p>
     * ------
     * Light
     * ------
     * 
     * <pre>
     * device Light extends Appliance {
     * }
     * </pre>
     */
    protected final static class LightDiscovererForInactivityLevelFromInactivitySensor {
        private Service serviceParent;
        
        private LightDiscovererForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private LightCompositeForInactivityLevelFromInactivitySensor instantiateComposite() {
            return new LightCompositeForInactivityLevelFromInactivitySensor(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices
         * 
         * @return a {@link LightCompositeForInactivityLevelFromInactivitySensor} object composed of all discoverable <code>Light</code>
         */
        public LightCompositeForInactivityLevelFromInactivitySensor all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>Light</code> devices
         * 
         * @return a {@link LightProxyForInactivityLevelFromInactivitySensor} object pointing to a random discoverable <code>Light</code> device
         */
        public LightProxyForInactivityLevelFromInactivitySensor anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link LightCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>Light</code> devices
         */
        public LightCompositeForInactivityLevelFromInactivitySensor whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link LightCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>Light</code> devices
         */
        public LightCompositeForInactivityLevelFromInactivitySensor whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link LightCompositeForInactivityLevelFromInactivitySensor} object composed of all matching <code>Light</code> devices
         */
        public LightCompositeForInactivityLevelFromInactivitySensor whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>Light</code> devices to get their sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * <p>
     * ------
     * Light
     * ------
     * 
     * <pre>
     * device Light extends Appliance {
     * }
     * </pre>
     */
    protected final static class LightCompositeForInactivityLevelFromInactivitySensor extends fr.inria.diagen.core.service.composite.Composite<LightProxyForInactivityLevelFromInactivitySensor> {
        private LightCompositeForInactivityLevelFromInactivitySensor(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Appliance/Light/");
        }
        
        @Override
        protected LightProxyForInactivityLevelFromInactivitySensor instantiateProxy(RemoteServiceInfo rsi) {
            return new LightProxyForInactivityLevelFromInactivitySensor(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link LightCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>id</code>.
         */
        public LightCompositeForInactivityLevelFromInactivitySensor andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link LightCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>location</code>.
         */
        public LightCompositeForInactivityLevelFromInactivitySensor andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link LightCompositeForInactivityLevelFromInactivitySensor}, filtered using the attribute <code>user</code>.
         */
        public LightCompositeForInactivityLevelFromInactivitySensor andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>Light</code> device to get its sources for the
     * <code>when provided inactivityLevel from InactivitySensor</code> interaction contract.
     * <p>
     * ------
     * Light
     * ------
     * 
     * <pre>
     * device Light extends Appliance {
     * }
     * </pre>
     */
    protected final static class LightProxyForInactivityLevelFromInactivitySensor extends Proxy {
        private LightProxyForInactivityLevelFromInactivitySensor(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Returns the value of the <code>on</code> source of this <code>Light</code> device
         * 
         * @return the value of the <code>on</code> source
         */
        public java.lang.Boolean getOn() throws InvocationException {
            return (java.lang.Boolean) callGetValue("on");
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
