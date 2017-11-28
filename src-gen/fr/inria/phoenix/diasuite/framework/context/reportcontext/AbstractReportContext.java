package fr.inria.phoenix.diasuite.framework.context.reportcontext;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.context.triggercontext.TriggerContextValue;

/**
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
@SuppressWarnings("all")
public abstract class AbstractReportContext extends Service {
    
    public AbstractReportContext(ServiceConfiguration serviceConfiguration) {
        super("/Context/ReportContext/", serviceConfiguration);
    }
    
    // Methods from the Service class
    @Override
    protected final void internalPostInitialize() {
        subscribeValue("triggerContext", "/Context/TriggerContext/"); // subscribe to TriggerContext context
        postInitialize();
    }
    
    @Override
    public final void valueReceived(java.util.Map<String, Object> properties, RemoteServiceInfo source, String eventName, Object value, Object... indexes) {
        if (eventName.equals("triggerContext") && source.isCompatible("/Context/TriggerContext/")) {
            TriggerContextValue triggerContextValue = new TriggerContextValue((java.lang.Boolean) value);
            
            ReportContextValuePublishable returnValue = onTriggerContext(triggerContextValue, new DiscoverForTriggerContext());
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
     * This method is called when the <code>TriggerContext</code> context publishes a value.
     * 
     * <pre>
     * when provided TriggerContext
     * 	   		get contact from ContactSensor,
     * 	   		consumption from ElectricMeter,
     * 	   		on from Light
     * 	maybe publish;
     * </pre>
     * 
     * @param triggerContextValue the value of the <code>TriggerContext</code> context.
     * @param discover a discover object to get value from devices and contexts
     * @return a {@link ReportContextValuePublishable} that says if the context should publish a value and which value it should publish
     */
    protected abstract ReportContextValuePublishable onTriggerContext(TriggerContextValue triggerContextValue, DiscoverForTriggerContext discover);
    
    // End of interaction contract implementation
    
    // Discover object for TriggerContext
    /**
     * An object to discover devices and contexts for the following interaction contract:
     * 
     * <code>
     * when provided TriggerContext
     * 	   		get contact from ContactSensor,
     * 	   		consumption from ElectricMeter,
     * 	   		on from Light
     * 	maybe publish;
     * </code>
     */
    protected final class DiscoverForTriggerContext {
        private final ContactSensorDiscovererForTriggerContext contactSensorDiscoverer = new ContactSensorDiscovererForTriggerContext(AbstractReportContext.this);
        private final ElectricMeterDiscovererForTriggerContext electricMeterDiscoverer = new ElectricMeterDiscovererForTriggerContext(AbstractReportContext.this);
        private final LightDiscovererForTriggerContext lightDiscoverer = new LightDiscovererForTriggerContext(AbstractReportContext.this);
        
        /**
         * @return a {@link ContactSensorDiscovererForTriggerContext} object to discover <code>ContactSensor</code> devices
         */
        public ContactSensorDiscovererForTriggerContext contactSensors() {
            return contactSensorDiscoverer;
        }
        
        /**
         * @return a {@link ElectricMeterDiscovererForTriggerContext} object to discover <code>ElectricMeter</code> devices
         */
        public ElectricMeterDiscovererForTriggerContext electricMeters() {
            return electricMeterDiscoverer;
        }
        
        /**
         * @return a {@link LightDiscovererForTriggerContext} object to discover <code>Light</code> devices
         */
        public LightDiscovererForTriggerContext lights() {
            return lightDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>ContactSensor</code> devices to get their sources for the
     * <code>when provided TriggerContext</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorDiscovererForTriggerContext {
        private Service serviceParent;
        
        private ContactSensorDiscovererForTriggerContext(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private ContactSensorCompositeForTriggerContext instantiateComposite() {
            return new ContactSensorCompositeForTriggerContext(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices
         * 
         * @return a {@link ContactSensorCompositeForTriggerContext} object composed of all discoverable <code>ContactSensor</code>
         */
        public ContactSensorCompositeForTriggerContext all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>ContactSensor</code> devices
         * 
         * @return a {@link ContactSensorProxyForTriggerContext} object pointing to a random discoverable <code>ContactSensor</code> device
         */
        public ContactSensorProxyForTriggerContext anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForTriggerContext} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForTriggerContext whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForTriggerContext} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForTriggerContext whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForTriggerContext} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForTriggerContext whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>ContactSensor</code> devices to get their sources for the
     * <code>when provided TriggerContext</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorCompositeForTriggerContext extends fr.inria.diagen.core.service.composite.Composite<ContactSensorProxyForTriggerContext> {
        private ContactSensorCompositeForTriggerContext(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Sensor/ContactSensor/");
        }
        
        @Override
        protected ContactSensorProxyForTriggerContext instantiateProxy(RemoteServiceInfo rsi) {
            return new ContactSensorProxyForTriggerContext(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForTriggerContext}, filtered using the attribute <code>id</code>.
         */
        public ContactSensorCompositeForTriggerContext andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForTriggerContext}, filtered using the attribute <code>location</code>.
         */
        public ContactSensorCompositeForTriggerContext andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForTriggerContext}, filtered using the attribute <code>user</code>.
         */
        public ContactSensorCompositeForTriggerContext andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>ContactSensor</code> device to get its sources for the
     * <code>when provided TriggerContext</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorProxyForTriggerContext extends Proxy {
        private ContactSensorProxyForTriggerContext(Service service, RemoteServiceInfo remoteServiceInfo) {
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
     * <code>when provided TriggerContext</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterDiscovererForTriggerContext {
        private Service serviceParent;
        
        private ElectricMeterDiscovererForTriggerContext(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private ElectricMeterCompositeForTriggerContext instantiateComposite() {
            return new ElectricMeterCompositeForTriggerContext(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices
         * 
         * @return a {@link ElectricMeterCompositeForTriggerContext} object composed of all discoverable <code>ElectricMeter</code>
         */
        public ElectricMeterCompositeForTriggerContext all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>ElectricMeter</code> devices
         * 
         * @return a {@link ElectricMeterProxyForTriggerContext} object pointing to a random discoverable <code>ElectricMeter</code> device
         */
        public ElectricMeterProxyForTriggerContext anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForTriggerContext} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForTriggerContext whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForTriggerContext} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForTriggerContext whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>ElectricMeter</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link ElectricMeterCompositeForTriggerContext} object composed of all matching <code>ElectricMeter</code> devices
         */
        public ElectricMeterCompositeForTriggerContext whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>ElectricMeter</code> devices to get their sources for the
     * <code>when provided TriggerContext</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterCompositeForTriggerContext extends fr.inria.diagen.core.service.composite.Composite<ElectricMeterProxyForTriggerContext> {
        private ElectricMeterCompositeForTriggerContext(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Sensor/EnergyMeter/ElectricMeter/");
        }
        
        @Override
        protected ElectricMeterProxyForTriggerContext instantiateProxy(RemoteServiceInfo rsi) {
            return new ElectricMeterProxyForTriggerContext(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForTriggerContext}, filtered using the attribute <code>id</code>.
         */
        public ElectricMeterCompositeForTriggerContext andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForTriggerContext}, filtered using the attribute <code>location</code>.
         */
        public ElectricMeterCompositeForTriggerContext andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link ElectricMeterCompositeForTriggerContext}, filtered using the attribute <code>user</code>.
         */
        public ElectricMeterCompositeForTriggerContext andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>ElectricMeter</code> device to get its sources for the
     * <code>when provided TriggerContext</code> interaction contract.
     * 
     * <pre>
     * device ElectricMeter extends EnergyMeter {
     * 	source consumption as Float;
     * }
     * </pre>
     */
    protected final static class ElectricMeterProxyForTriggerContext extends Proxy {
        private ElectricMeterProxyForTriggerContext(Service service, RemoteServiceInfo remoteServiceInfo) {
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
    
    /**
     * Discover object that will exposes the <code>Light</code> devices to get their sources for the
     * <code>when provided TriggerContext</code> interaction contract.
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
    protected final static class LightDiscovererForTriggerContext {
        private Service serviceParent;
        
        private LightDiscovererForTriggerContext(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private LightCompositeForTriggerContext instantiateComposite() {
            return new LightCompositeForTriggerContext(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices
         * 
         * @return a {@link LightCompositeForTriggerContext} object composed of all discoverable <code>Light</code>
         */
        public LightCompositeForTriggerContext all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>Light</code> devices
         * 
         * @return a {@link LightProxyForTriggerContext} object pointing to a random discoverable <code>Light</code> device
         */
        public LightProxyForTriggerContext anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link LightCompositeForTriggerContext} object composed of all matching <code>Light</code> devices
         */
        public LightCompositeForTriggerContext whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link LightCompositeForTriggerContext} object composed of all matching <code>Light</code> devices
         */
        public LightCompositeForTriggerContext whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>Light</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link LightCompositeForTriggerContext} object composed of all matching <code>Light</code> devices
         */
        public LightCompositeForTriggerContext whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>Light</code> devices to get their sources for the
     * <code>when provided TriggerContext</code> interaction contract.
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
    protected final static class LightCompositeForTriggerContext extends fr.inria.diagen.core.service.composite.Composite<LightProxyForTriggerContext> {
        private LightCompositeForTriggerContext(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Appliance/Light/");
        }
        
        @Override
        protected LightProxyForTriggerContext instantiateProxy(RemoteServiceInfo rsi) {
            return new LightProxyForTriggerContext(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link LightCompositeForTriggerContext}, filtered using the attribute <code>id</code>.
         */
        public LightCompositeForTriggerContext andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link LightCompositeForTriggerContext}, filtered using the attribute <code>location</code>.
         */
        public LightCompositeForTriggerContext andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link LightCompositeForTriggerContext}, filtered using the attribute <code>user</code>.
         */
        public LightCompositeForTriggerContext andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>Light</code> device to get its sources for the
     * <code>when provided TriggerContext</code> interaction contract.
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
    protected final static class LightProxyForTriggerContext extends Proxy {
        private LightProxyForTriggerContext(Service service, RemoteServiceInfo remoteServiceInfo) {
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
    // End of discover object for TriggerContext
}
