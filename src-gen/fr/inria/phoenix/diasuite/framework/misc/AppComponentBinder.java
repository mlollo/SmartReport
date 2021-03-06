package fr.inria.phoenix.diasuite.framework.misc;

import fr.inria.diagen.core.deploy.AbstractDeploy;

import fr.inria.phoenix.diasuite.framework.context.reportcontext.AbstractReportContext;
import fr.inria.phoenix.diasuite.framework.context.triggercontext.AbstractTriggerContext;

import fr.inria.phoenix.diasuite.framework.controller.reportcontroller.AbstractReportController;

/**
 * This class should be implemented to bind the implementation of the various components
 */
public abstract class AppComponentBinder extends AbstractDeploy {

    // Context instances
    private AbstractReportContext reportContextInstance = null;
    private AbstractTriggerContext triggerContextInstance = null;

    // Controller instances
    private AbstractReportController reportControllerInstance = null;
    
    @Override
    public void deployAll() {
        // Initialization of contexts
        if (reportContextInstance == null)
            reportContextInstance = getInstance(getReportContextClass());
        if (triggerContextInstance == null)
            triggerContextInstance = getInstance(getTriggerContextClass());
        // Intialization of controllers
        if (reportControllerInstance == null)
            reportControllerInstance = getInstance(getReportControllerClass());
        // Deploying contexts
        deploy(reportContextInstance);
        deploy(triggerContextInstance);
        // Deploying controllers
        deploy(reportControllerInstance);
    }
    
    @Override
    public void undeployAll() {
        // Undeploying contexts
        undeploy(reportContextInstance);
        undeploy(triggerContextInstance);
        // Undeploying controllers
        undeploy(reportControllerInstance);
    }
    
    // Abstract binding methods for contexts
    /**
     * Overrides this method to provide the implementation class of the <code>ReportContext</code> context
     * 
     * <pre>
     * context ReportContext as SensorReport[] {
     * 	when provided TriggerContext
     * 	   		get contact from ContactSensor,
     * 	   		consumption from ElectricMeter
     * 	maybe publish;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractReportContext} that implements the <code>ReportContext</code> context
     */
    public abstract Class<? extends AbstractReportContext> getReportContextClass();
    
    /**
     * Overrides this method to provide the implementation class of the <code>TriggerContext</code> context
     * 
     * <pre>
     * context TriggerContext as Boolean {
     * 	when provided inactivityLevel from InactivitySensor
     * 	   		get lastInteraction from InactivitySensor,
     * 	   		on from Light
     * 	maybe publish;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractTriggerContext} that implements the <code>TriggerContext</code> context
     */
    public abstract Class<? extends AbstractTriggerContext> getTriggerContextClass();
    
    // End of abstract binding methods for contexts
    
    // Abstract binding methods for controllers
    /**
     * Overrides this method to provide the implementation class of the <code>ReportController</code> controller
     * 
     * <pre>
     * controller ReportController {
     * 	when provided ReportContext do SendMessage on Messenger;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractReportController} that implements the <code>ReportController</code> controller
     */
    public abstract Class<? extends AbstractReportController> getReportControllerClass();
    
    // End of abstract binding methods for controllers
}
