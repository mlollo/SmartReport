package fr.inria.phoenix.diasuite.framework.context.contactsensorexpected;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.context.checkexit.CheckExitValue;

/**
 * <pre>
 * context ContactSensorExpected as SensorReport indexed by id as Integer {
 * 	when provided CheckExit 
 * 	   		get contact from ContactSensor
 * 	maybe publish;
 * }
 * </pre>
 */
@SuppressWarnings("all")
public abstract class AbstractContactSensorExpected extends Service {
    
    public AbstractContactSensorExpected(ServiceConfiguration serviceConfiguration) {
        super("/Context/ContactSensorExpected/", serviceConfiguration);
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
            
            ContactSensorExpectedValuePublishable returnValue = onCheckExit(checkExitValue, new DiscoverForCheckExit());
            if(returnValue != null && returnValue.doPublish()) {
                setContactSensorExpected(returnValue.getValue(), returnValue.getId());
            }
        }
    }
    
    @Override
    public final Object getValueCalled(java.util.Map<String, Object> properties, RemoteServiceInfo source, String valueName,
            Object... indexes) throws Exception {
        if (valueName.equals("contactSensorExpected")) {
            return getLastValue((java.lang.Integer) indexes[0]);
        }
        throw new InvocationException("Unsupported method call: " + valueName);
    }
    // End of methods from the Service class
    
    // Code relative to the return value of the context
    private java.util.Map<ContactSensorExpectedIndices, fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> contextValues = new java.util.HashMap<ContactSensorExpectedIndices, fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport>();
    
    private void setContactSensorExpected(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport newContextValue, java.lang.Integer id) {
        ContactSensorExpectedIndices indices = new ContactSensorExpectedIndices(id);
        contextValues.put(indices, newContextValue);
        getProcessor().publishValue(getOutProperties(), "contactSensorExpected", newContextValue, id);
    }
    
    /**
     * Get the last value of the context
     * 
     * @param id the index <code>id</code> for the last value
     * @return the latest value published by the context for the given indexes
     */
    protected final fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport getLastValue(java.lang.Integer id) {
        ContactSensorExpectedIndices indices = new ContactSensorExpectedIndices(id);
        return contextValues.get(indices);
    }
    
    /**
     * A class that represents a value that might be published for the <code>ContactSensorExpected</code> context. It is used by
     * event methods that might or might not publish values for this context.
     */
    protected final static class ContactSensorExpectedValuePublishable {
        
        // The value of the context
        private fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value;
        // The id index
        private java.lang.Integer id;
        // Whether the value should be published or not
        private boolean doPublish;
        
        public ContactSensorExpectedValuePublishable(fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport value,
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
     * 	   		get contact from ContactSensor
     * 	maybe publish;
     * </pre>
     * 
     * @param checkExitValue the value of the <code>CheckExit</code> context.
     * @param discover a discover object to get value from devices and contexts
     * @return a {@link ContactSensorExpectedValuePublishable} that says if the context should publish a value and which value it should publish
     */
    protected abstract ContactSensorExpectedValuePublishable onCheckExit(CheckExitValue checkExitValue, DiscoverForCheckExit discover);
    
    // End of interaction contract implementation
    
    // Discover object for CheckExit
    /**
     * An object to discover devices and contexts for the following interaction contract:
     * 
     * <code>
     * when provided CheckExit 
     * 	   		get contact from ContactSensor
     * 	maybe publish;
     * </code>
     */
    protected final class DiscoverForCheckExit {
        private final ContactSensorDiscovererForCheckExit contactSensorDiscoverer = new ContactSensorDiscovererForCheckExit(AbstractContactSensorExpected.this);
        
        /**
         * @return a {@link ContactSensorDiscovererForCheckExit} object to discover <code>ContactSensor</code> devices
         */
        public ContactSensorDiscovererForCheckExit contactSensors() {
            return contactSensorDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>ContactSensor</code> devices to get their sources for the
     * <code>when provided CheckExit</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorDiscovererForCheckExit {
        private Service serviceParent;
        
        private ContactSensorDiscovererForCheckExit(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private ContactSensorCompositeForCheckExit instantiateComposite() {
            return new ContactSensorCompositeForCheckExit(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices
         * 
         * @return a {@link ContactSensorCompositeForCheckExit} object composed of all discoverable <code>ContactSensor</code>
         */
        public ContactSensorCompositeForCheckExit all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>ContactSensor</code> devices
         * 
         * @return a {@link ContactSensorProxyForCheckExit} object pointing to a random discoverable <code>ContactSensor</code> device
         */
        public ContactSensorProxyForCheckExit anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForCheckExit} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForCheckExit whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>location</code> matches a given value.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForCheckExit} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForCheckExit whereLocation(java.lang.String location) throws CompositeException {
            return instantiateComposite().andLocation(location);
        }
        
        /**
         * Returns a composite of all accessible <code>ContactSensor</code> devices whose attribute <code>user</code> matches a given value.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return a {@link ContactSensorCompositeForCheckExit} object composed of all matching <code>ContactSensor</code> devices
         */
        public ContactSensorCompositeForCheckExit whereUser(java.lang.String user) throws CompositeException {
            return instantiateComposite().andUser(user);
        }
    }
    
    /**
     * A composite of several <code>ContactSensor</code> devices to get their sources for the
     * <code>when provided CheckExit</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorCompositeForCheckExit extends fr.inria.diagen.core.service.composite.Composite<ContactSensorProxyForCheckExit> {
        private ContactSensorCompositeForCheckExit(Service serviceParent) {
            super(serviceParent, "/Device/Device/PhysicalDevice/Sensor/ContactSensor/");
        }
        
        @Override
        protected ContactSensorProxyForCheckExit instantiateProxy(RemoteServiceInfo rsi) {
            return new ContactSensorProxyForCheckExit(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForCheckExit}, filtered using the attribute <code>id</code>.
         */
        public ContactSensorCompositeForCheckExit andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>location</code>.
         * 
         * @param location The <code>location<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForCheckExit}, filtered using the attribute <code>location</code>.
         */
        public ContactSensorCompositeForCheckExit andLocation(java.lang.String location) throws CompositeException {
            filterByAttribute("location", location);
            return this;
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>user</code>.
         * 
         * @param user The <code>user<code> attribute value to match.
         * @return this {@link ContactSensorCompositeForCheckExit}, filtered using the attribute <code>user</code>.
         */
        public ContactSensorCompositeForCheckExit andUser(java.lang.String user) throws CompositeException {
            filterByAttribute("user", user);
            return this;
        }
    }
    
    /**
     * A proxy to one <code>ContactSensor</code> device to get its sources for the
     * <code>when provided CheckExit</code> interaction contract.
     * 
     * <pre>
     * device ContactSensor extends Sensor {
     * 	source contact as Boolean;
     * }
     * </pre>
     */
    protected final static class ContactSensorProxyForCheckExit extends Proxy {
        private ContactSensorProxyForCheckExit(Service service, RemoteServiceInfo remoteServiceInfo) {
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
    // End of discover object for CheckExit
}
