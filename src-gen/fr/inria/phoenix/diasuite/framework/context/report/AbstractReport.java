package fr.inria.phoenix.diasuite.framework.context.report;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.context.contactsensorexpected.ContactSensorExpectedValue;
import fr.inria.phoenix.diasuite.framework.context.electricsensorexpected.ElectricSensorExpectedValue;

/**
 * <pre>
 * context Report as SensorReport[] {
 * 	when provided ContactSensorExpected 
 * 	always publish;
 * 	when provided ElectricSensorExpected 
 * 	always publish;
 * }
 * </pre>
 */
@SuppressWarnings("all")
public abstract class AbstractReport extends Service {
    
    public AbstractReport(ServiceConfiguration serviceConfiguration) {
        super("/Context/Report/", serviceConfiguration);
    }
    
    // Methods from the Service class
    @Override
    protected final void internalPostInitialize() {
        subscribeValue("contactSensorExpected", "/Context/ContactSensorExpected/"); // subscribe to ContactSensorExpected context
        subscribeValue("electricSensorExpected", "/Context/ElectricSensorExpected/"); // subscribe to ElectricSensorExpected context
        postInitialize();
    }
    
    @Override
    public final void valueReceived(java.util.Map<String, Object> properties, RemoteServiceInfo source, String eventName, Object value, Object... indexes) {
        if (eventName.equals("contactSensorExpected") && source.isCompatible("/Context/ContactSensorExpected/")) {
            ContactSensorExpectedValue contactSensorExpectedValue = new ContactSensorExpectedValue((fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport) value,
                    (java.lang.Integer) indexes[0]);
            
            setReport(onContactSensorExpected(contactSensorExpectedValue));
        }
        if (eventName.equals("electricSensorExpected") && source.isCompatible("/Context/ElectricSensorExpected/")) {
            ElectricSensorExpectedValue electricSensorExpectedValue = new ElectricSensorExpectedValue((fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport) value,
                    (java.lang.Integer) indexes[0]);
            
            setReport(onElectricSensorExpected(electricSensorExpectedValue));
        }
    }
    
    @Override
    public final Object getValueCalled(java.util.Map<String, Object> properties, RemoteServiceInfo source, String valueName,
            Object... indexes) throws Exception {
        if (valueName.equals("report")) {
            return getLastValue();
        }
        throw new InvocationException("Unsupported method call: " + valueName);
    }
    // End of methods from the Service class
    
    // Code relative to the return value of the context
    private java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> contextValue;
    
    private void setReport(java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> newContextValue) {
        contextValue = newContextValue;
        getProcessor().publishValue(getOutProperties(), "report", newContextValue);
    }
    
    /**
     * Get the last value of the context
     * 
     * @return the latest value published by the context
     */
    protected final java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> getLastValue() {
        return contextValue;
    }
    // End of code relative to the return value of the context
    
    // Interaction contract implementation
    /**
     * This method is called when the <code>ContactSensorExpected</code> context publishes a value.
     * 
     * <pre>
     * when provided ContactSensorExpected 
     * 	always publish;
     * </pre>
     * 
     * @param contactSensorExpectedValue the value of the <code>ContactSensorExpected</code> context.
     * @return the value to publish
     */
    protected abstract java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> onContactSensorExpected(ContactSensorExpectedValue contactSensorExpectedValue);
    
    /**
     * This method is called when the <code>ElectricSensorExpected</code> context publishes a value.
     * 
     * <pre>
     * when provided ElectricSensorExpected 
     * 	always publish;
     * </pre>
     * 
     * @param electricSensorExpectedValue the value of the <code>ElectricSensorExpected</code> context.
     * @return the value to publish
     */
    protected abstract java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport> onElectricSensorExpected(ElectricSensorExpectedValue electricSensorExpectedValue);
    
    // End of interaction contract implementation
}
