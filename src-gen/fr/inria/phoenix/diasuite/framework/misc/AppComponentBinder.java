package fr.inria.phoenix.diasuite.framework.misc;

import fr.inria.diagen.core.deploy.AbstractDeploy;

import fr.inria.phoenix.diasuite.framework.context.checkexit.AbstractCheckExit;
import fr.inria.phoenix.diasuite.framework.context.contactsensorexpected.AbstractContactSensorExpected;
import fr.inria.phoenix.diasuite.framework.context.electricsensorexpected.AbstractElectricSensorExpected;
import fr.inria.phoenix.diasuite.framework.context.report.AbstractReport;

import fr.inria.phoenix.diasuite.framework.controller.reportcontroller.AbstractReportController;

/**
 * This class should be implemented to bind the implementation of the various components
 */
public abstract class AppComponentBinder extends AbstractDeploy {

    // Context instances
    private AbstractCheckExit checkExitInstance = null;
    private AbstractContactSensorExpected contactSensorExpectedInstance = null;
    private AbstractElectricSensorExpected electricSensorExpectedInstance = null;
    private AbstractReport reportInstance = null;

    // Controller instances
    private AbstractReportController reportControllerInstance = null;
    
    @Override
    public void deployAll() {
        // Initialization of contexts
        if (checkExitInstance == null)
            checkExitInstance = getInstance(getCheckExitClass());
        if (contactSensorExpectedInstance == null)
            contactSensorExpectedInstance = getInstance(getContactSensorExpectedClass());
        if (electricSensorExpectedInstance == null)
            electricSensorExpectedInstance = getInstance(getElectricSensorExpectedClass());
        if (reportInstance == null)
            reportInstance = getInstance(getReportClass());
        // Intialization of controllers
        if (reportControllerInstance == null)
            reportControllerInstance = getInstance(getReportControllerClass());
        // Deploying contexts
        deploy(checkExitInstance);
        deploy(contactSensorExpectedInstance);
        deploy(electricSensorExpectedInstance);
        deploy(reportInstance);
        // Deploying controllers
        deploy(reportControllerInstance);
    }
    
    @Override
    public void undeployAll() {
        // Undeploying contexts
        undeploy(checkExitInstance);
        undeploy(contactSensorExpectedInstance);
        undeploy(electricSensorExpectedInstance);
        undeploy(reportInstance);
        // Undeploying controllers
        undeploy(reportControllerInstance);
    }
    
    // Abstract binding methods for contexts
    /**
     * Overrides this method to provide the implementation class of the <code>CheckExit</code> context
     * 
     * <pre>
     * context CheckExit as Boolean {
     * 	when provided inactivityLevel from InactivitySensor
     * 	   		get lastInteraction from InactivitySensor
     * 	maybe publish;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractCheckExit} that implements the <code>CheckExit</code> context
     */
    public abstract Class<? extends AbstractCheckExit> getCheckExitClass();
    
    /**
     * Overrides this method to provide the implementation class of the <code>ContactSensorExpected</code> context
     * 
     * <pre>
     * context ContactSensorExpected as SensorReport indexed by id as Integer {
     * 	when provided CheckExit 
     * 	   		get contact from ContactSensor
     * 	maybe publish;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractContactSensorExpected} that implements the <code>ContactSensorExpected</code> context
     */
    public abstract Class<? extends AbstractContactSensorExpected> getContactSensorExpectedClass();
    
    /**
     * Overrides this method to provide the implementation class of the <code>ElectricSensorExpected</code> context
     * 
     * <pre>
     * context ElectricSensorExpected as SensorReport indexed by id as Integer {
     * 	when provided CheckExit 
     * 	   		get consumption from ElectricMeter
     * 	maybe publish;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractElectricSensorExpected} that implements the <code>ElectricSensorExpected</code> context
     */
    public abstract Class<? extends AbstractElectricSensorExpected> getElectricSensorExpectedClass();
    
    /**
     * Overrides this method to provide the implementation class of the <code>Report</code> context
     * 
     * <pre>
     * context Report as SensorReport[] {
     * 	when provided ContactSensorExpected 
     * 	always publish;
     * 	when provided ElectricSensorExpected 
     * 	always publish;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractReport} that implements the <code>Report</code> context
     */
    public abstract Class<? extends AbstractReport> getReportClass();
    
    // End of abstract binding methods for contexts
    
    // Abstract binding methods for controllers
    /**
     * Overrides this method to provide the implementation class of the <code>ReportController</code> controller
     * 
     * <pre>
     * controller ReportController {
     * 	when provided Report do SendMessage on Messenger;
     * }
     * </pre>
     * @return a class object of a derivation of {@link AbstractReportController} that implements the <code>ReportController</code> controller
     */
    public abstract Class<? extends AbstractReportController> getReportControllerClass();
    
    // End of abstract binding methods for controllers
}
