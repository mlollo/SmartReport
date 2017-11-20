package fr.inria.phoenix.scenario.smartreport;
        
import fr.inria.phoenix.diasuite.framework.context.checkexit.AbstractCheckExit;
import fr.inria.phoenix.diasuite.framework.context.contactsensorexpected.AbstractContactSensorExpected;
import fr.inria.phoenix.diasuite.framework.context.electricsensorexpected.AbstractElectricSensorExpected;
import fr.inria.phoenix.diasuite.framework.context.report.AbstractReport;
import fr.inria.phoenix.diasuite.framework.controller.reportcontroller.AbstractReportController;
import fr.inria.phoenix.diasuite.framework.misc.AppComponentBinder;
import fr.inria.phoenix.scenario.smartreport.context.CheckExit;
import fr.inria.phoenix.scenario.smartreport.context.ContactSensorExpected;
import fr.inria.phoenix.scenario.smartreport.context.ElectricSensorExpected;
import fr.inria.phoenix.scenario.smartreport.context.Report;
import fr.inria.phoenix.scenario.smartreport.controller.ReportController;

/* (non-Javadoc)
 * The binder to provides the various components of the application
 * @see fr.inria.phoenix.diasuite.framework.misc.AppComponentBinder
 */
public class ComponentBinder extends AppComponentBinder {

	public Class<? extends AbstractCheckExit> getCheckExitClass() {
		// TODO Auto-generated method stub
		return CheckExit.class;
	}

	public Class<? extends AbstractContactSensorExpected> getContactSensorExpectedClass() {
		// TODO Auto-generated method stub
		return ContactSensorExpected.class;
	}

	public Class<? extends AbstractElectricSensorExpected> getElectricSensorExpectedClass() {
		// TODO Auto-generated method stub
		return ElectricSensorExpected.class;
	}

	public Class<? extends AbstractReport> getReportClass() {
		// TODO Auto-generated method stub
		return Report.class;
	}

	public Class<? extends AbstractReportController> getReportControllerClass() {
		// TODO Auto-generated method stub
		return ReportController.class;
	}
}
