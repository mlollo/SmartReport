package fr.inria.phoenix.diasuite.framework.device.mailer;

import java.io.Serializable;

import fr.inria.diagen.core.network.RemoteServiceInfo;
import fr.inria.diagen.core.service.local.Service;
import fr.inria.diagen.core.service.proxy.Proxy;

/**
 * An object to store a value published on the source <code>unreadEmailCount</code> of the device <code>Mailer</code>.
 *
 * <pre>
 * device Mailer extends Messenger {
 * 	source unreadEmailCount as Integer;
 * }
 * </pre>
 */
public final class UnreadEmailCountFromMailer implements Serializable {
    private static final long serialVersionUID = 0;
    
    private java.lang.Integer value;
    
    /**
     * Get the value of the source <code>unreadEmailCount</code>
     * 
     * @return the value of the source <code>unreadEmailCount</code>
     */
    public java.lang.Integer value() {
        return value;
    }
    
    public UnreadEmailCountFromMailer(Service service, RemoteServiceInfo remoteServiceInfo, java.lang.Integer value) {
        this.sender = new MailerProxy(service, remoteServiceInfo);
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
        UnreadEmailCountFromMailer other = (UnreadEmailCountFromMailer) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
    // Proxy to the sender, i.e., the device that published the source
    private MailerProxy sender;
    
    /**
     * Get the sender of the source <code>unreadEmailCount</code>. I.e., the <code>Mailer</code> device that published the source.
     * 
     * @return A proxy to the <code>Mailer</code> that triggered the source
     */
    public MailerProxy sender() {
        return sender;
    }
    
    /**
     * A proxy to a <code>Mailer</code> that discloses subscription/unsubscription methods.
     */
    public class MailerProxy extends Proxy {
        private MailerProxy(Service service, RemoteServiceInfo remoteServiceInfo) {
            super(service, remoteServiceInfo);
        }
        
        /**
         * Subcribes to publication of source <code>unreadEmailCount</code> from this <code>Mailer</code>.
         */
        public void subscribeUnreadEmailCount() {
            getService().getProcessor().subscribeValue(getService().getOutProperties(), new fr.inria.diagen.core.service.filter.SubscriptionFilter(this.getRemoteServiceInfo()), "unreadEmailCount");
        }
        
        /**
         * Unsubcribes from publication of source <code>unreadEmailCount</code> from this <code>Mailer</code>.
         */
        public void unsubscribeUnreadEmailCount() {
            getService().getProcessor().unsubscribeValue(getService().getOutProperties(), new fr.inria.diagen.core.service.filter.SubscriptionFilter(this.getRemoteServiceInfo()), "unreadEmailCount");
        }
        
        /**
         * @return the value of the <code>id</code> attribute
         */
        public java.lang.String id() {
            return (java.lang.String) callGetValue("id");
        }
    }
}
