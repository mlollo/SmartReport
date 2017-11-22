package fr.inria.phoenix.diasuite.framework.controller.reportcontroller;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.diagen.core.service.composite.CompositeException;
import fr.inria.diagen.core.exception.InvocationException;
import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

import fr.inria.phoenix.diasuite.framework.context.reportcontext.ReportContextValue;

/**
 * <pre>
 * controller ReportController {
 * 	when provided ReportContext do SendMessage on Messenger;
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
        subscribeValue("reportContext", "/Context/ReportContext/"); // subscribe to ReportContext context
        postInitialize();
    }
    
    @Override
    public final void valueReceived(java.util.Map<String, Object> properties, RemoteServiceInfo source, String eventName, Object value, Object... indexes) {
        if (eventName.equals("reportContext") && source.isCompatible("/Context/ReportContext/")) {
            ReportContextValue reportContextValue = new ReportContextValue((java.util.List<fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport>) value);
            
            onReportContext(reportContextValue, new DiscoverForReportContext());
        }
    }
    // End of methods from the Service class
    
    // Interaction contract implementation
    /**
     * This method is called when the <code>ReportContext</code> context publishes a value.
     * 
     * <pre>
     * when provided ReportContext do SendMessage on Messenger;
     * </pre>
     * 
     * @param reportContext the value of the <code>ReportContext</code> context.
     * @param discover a discover object to get context values and action methods
     */
    protected abstract void onReportContext(ReportContextValue reportContext, DiscoverForReportContext discover);
    
    // End of interaction contract implementation
    
    // Discover object for ReportContext
    /**
     * An object to discover devices and contexts for the following interaction contract:
     * 
     * <code>
     * when provided ReportContext do SendMessage on Messenger;
     * </code>
     */
    protected final class DiscoverForReportContext {
        private final MessengerDiscovererForReportContext messengerDiscoverer = new MessengerDiscovererForReportContext(AbstractReportController.this);
        
        /**
         * @return a {@link MessengerDiscovererForReportContext} object to discover <code>Messenger</code> devices
         */
        public MessengerDiscovererForReportContext messengers() {
            return messengerDiscoverer;
        }
    }
    
    /**
     * Discover object that will exposes the <code>Messenger</code> devices to execute action on for the
     * <code>when provided ReportContext</code> interaction contract.
     * 
     * <pre>
     * device Messenger extends CommunicationService {
     * 	source lastMessage as Message;
     * 	action SendMessage;
     * }
     * </pre>
     */
    protected final static class MessengerDiscovererForReportContext {
        private Service serviceParent;
        
        private MessengerDiscovererForReportContext(Service serviceParent) {
            super();
            this.serviceParent = serviceParent;
        }
        
        private MessengerCompositeForReportContext instantiateComposite() {
            return new MessengerCompositeForReportContext(serviceParent);
        }
        
        /**
         * Returns a composite of all accessible <code>Messenger</code> devices
         * 
         * @return a {@link MessengerCompositeForReportContext} object composed of all discoverable <code>Messenger</code>
         */
        public MessengerCompositeForReportContext all() {
            return instantiateComposite();
        }
        
        /**
         * Returns a proxy to one out of all accessible <code>Messenger</code> devices
         * 
         * @return a {@link MessengerProxyForReportContext} object pointing to a random discoverable <code>Messenger</code> device
         */
        public MessengerProxyForReportContext anyOne() {
            return all().anyOne();
        }
        
        /**
         * Returns a composite of all accessible <code>Messenger</code> devices whose attribute <code>id</code> matches a given value.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return a {@link MessengerCompositeForReportContext} object composed of all matching <code>Messenger</code> devices
         */
        public MessengerCompositeForReportContext whereId(java.lang.String id) throws CompositeException {
            return instantiateComposite().andId(id);
        }
    }
    
    /**
     * A composite of several <code>Messenger</code> devices to execute action on for the
     * <code>when provided ReportContext</code> interaction contract.
     * 
     * <pre>
     * device Messenger extends CommunicationService {
     * 	source lastMessage as Message;
     * 	action SendMessage;
     * }
     * </pre>
     */
    protected final static class MessengerCompositeForReportContext extends fr.inria.diagen.core.service.composite.Composite<MessengerProxyForReportContext> {
        private MessengerCompositeForReportContext(Service serviceParent) {
            super(serviceParent, "/Device/Device/Service/CommunicationService/Messenger/");
        }
        
        @Override
        protected MessengerProxyForReportContext instantiateProxy(RemoteServiceInfo rsi) {
            return new MessengerProxyForReportContext(service, rsi);
        }
        
        /**
         * Returns this composite in which a filter was set to the attribute <code>id</code>.
         * 
         * @param id The <code>id<code> attribute value to match.
         * @return this {@link MessengerCompositeForReportContext}, filtered using the attribute <code>id</code>.
         */
        public MessengerCompositeForReportContext andId(java.lang.String id) throws CompositeException {
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
            for (MessengerProxyForReportContext proxy : proxies) {
                proxy.sendMessage(message);
            }
        }
    }
    
    /**
     * A proxy to one <code>Messenger</code> device to execute action on for the
     * <code>when provided ReportContext</code> interaction contract.
     * 
     * <pre>
     * device Messenger extends CommunicationService {
     * 	source lastMessage as Message;
     * 	action SendMessage;
     * }
     * </pre>
     */
    protected final static class MessengerProxyForReportContext extends Proxy {
        private MessengerProxyForReportContext(Service service, RemoteServiceInfo remoteServiceInfo) {
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
    // End of discover object for ReportContext
}
