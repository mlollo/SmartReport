package fr.inria.phoenix.diasuite.framework.controller.reportcontroller;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.context.report.ReportValue;

/**
 * <pre>
 * controller ReportController {
 * 	when provided Report do SendMessage on Messenger;
 * }
 * </pre>
 */
@SuppressWarnings("all")
public abstract class AbstractReportController extends Service {
    
    public AbstractReportController(ServiceConfiguration serviceConfiguration) {
        super("/Controller/ReportController/", serviceConfiguration);
    }
    
    // Methods from the Service class
    @Override
    protected final void internalPostInitialize() {
        subscribeValue("report", "/Context/Report/"); // subscribe to Report context
        postInitialize();
    }
    
    @Override
    public final void valueReceived(java.util.Map<String, Object> properties, RemoteServiceInfo source, String eventName, Object value, Object... indexes) {
        if (eventName.equals("report") && source.isCompatible("/Context/Report/")) {
            ReportValue reportValue = new ReportValue((java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport>) value);
            
            onReport(reportValue, new DiscoverForReport());
        }
    }
    // End of methods from the Service class
    
    // Interaction contract implementation
    /**
     * This method is called when the <code>Report</code> context publishes a value.
     * 
     * <pre>
     * when provided Report do SendMessage on Messenger;
     * </pre>
     * 
     * @param report the value of the <code>Report</code> context.
     * @param discover a discover object to get context values and action methods
     */
    protected abstract void onReport(ReportValue report, DiscoverForReport discover);
    
    // End of interaction contract implementation
    
    // Discover object for Report
    /**
     * An object to discover devices and contexts for the following interaction contract:
     * 
     * <code>
     * when provided Report do SendMessage on Messenger;
     * </code>
     */
    protected final class DiscoverForReport {
        private final MessengerDiscovererForReport messengerDiscoverer = new MessengerDiscovererForReport(AbstractReportController.this);
        
        /**
         * @return a {@link MessengerDiscovererForReport} object to discover <code>Messenger</code> devices
         */
        public MessengerDiscovererForReport messengers() {
            return messengerDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>Messenger</code> devices to execute action on for the
     * <code>when provided Report</code> interaction contract.
     * 
     * <pre>
     * device Messenger extends CommunicationService {
     * 	source lastMessage as Message;
     * 	action SendMessage;
     * }
     * </pre>
     */
    protected final static class MessengerDiscovererForReport {
        private Service serviceParent;
        
        private MessengerDiscovererForReport(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private MessengerCompositeForReport instantiateComposite() {
            return new MessengerCompositeForReport(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>Messenger</code> devices
         * 
         * @return a {@link MessengerCompositeForReport} object composed of all discoverable <code>Messenger</code>
         */
        public MessengerCompositeForReport all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>Messenger</code> devices
         * 
         * @return a {@link MessengerProxyForReport} object pointing to a random discoverable <code>Messenger</code> device
         */
        public MessengerProxyForReport anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>Messenger</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link MessengerCompositeForReport} object composed of all matching <code>Messenger</code> devices
         */
        public MessengerCompositeForReport whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
    }
    
    /**
     * A composite of several <code>Messenger</code> devices to execute action on for the
     * <code>when provided Report</code> interaction contract.
     * 
     * <pre>
     * device Messenger extends CommunicationService {
     * 	source lastMessage as Message;
     * 	action SendMessage;
     * }
     * </pre>
     */
    protected final static class MessengerCompositeForReport extends fr.inria.diagen.core.service.composite.Composite<MessengerProxyForReport> {
        private MessengerCompositeForReport(Service serviceParent) {
            super(serviceParent, "/Device/Device/Service/CommunicationService/Messenger/");
        }
        
        @Override
        protected MessengerProxyForReport instantiateProxy(RemoteServiceInfo rsi) {
            return new MessengerProxyForReport(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link MessengerCompositeForReport}, filtered using the attribute <code>id</code>.
         */
        public MessengerCompositeForReport andId(java.lang.String id) throws CompositeException {
            filterByAttribute("id", id);
            return this;
        }
        
        /**
         * Executes the <code>sendMessage(message as Message)</code> action's method on all devices of this composite.
         * 
         * @param message the <code>message</code> parameter of the <code>sendMessage(message as Message)</code> method.
         */
        public void sendMessage(fr.inria.phoenix.diasuite.framework.datatype.message.Message message) throws InvocationException {
            launchDiscovering();
            for (MessengerProxyForReport proxy : proxies) {
                proxy.sendMessage(message);
            }
        }
    }
    
    /**
     * A proxy to one <code>Messenger</code> device to execute action on for the
     * <code>when provided Report</code> interaction contract.
     * 
     * <pre>
     * device Messenger extends CommunicationService {
     * 	source lastMessage as Message;
     * 	action SendMessage;
     * }
     * </pre>
     */
    protected final static class MessengerProxyForReport extends Proxy {
        private MessengerProxyForReport(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Executes the <code>sendMessage(message as Message)</code> action's method on this device.
         * 
         * @param message the <code>message</code> parameter of the <code>sendMessage(message as Message)</code> method.
         */
        public void sendMessage(fr.inria.phoenix.diasuite.framework.datatype.message.Message message) throws InvocationException {
            callOrder("sendMessage", message);
        }
        
        /**
         * @return the value of the <code>id</code> attribute
         */
        public java.lang.String id() {
            return (java.lang.String) callGetValue("id");
        }
    }
    // End of discover object for Report
}
