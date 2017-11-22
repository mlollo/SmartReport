package fr.inria.phoenix.scenario.smartreport;
        
import fr.inria.phoenix.diasuite.framework.context.reportcontext.AbstractReportContext;
import fr.inria.phoenix.diasuite.framework.controller.reportcontroller.AbstractReportController;
import fr.inria.phoenix.diasuite.framework.misc.AppComponentBinder;
import fr.inria.phoenix.scenario.smartreport.context.ReportContext;
import fr.inria.phoenix.scenario.smartreport.controller.ReportController;

/* (non-Javadoc)
 * The binder to provides the various components of the application
 * @see fr.inria.phoenix.diasuite.framework.misc.AppComponentBinder
 */
public class ComponentBinder extends AppComponentBinder {

	public Class<? extends AbstractReportContext> getReportContextClass() {
		// TODO Auto-generated method stub
		return ReportContext.class;
	}

	public Class<? extends AbstractReportController> getReportControllerClass() {
		// TODO Auto-generated method stub
		return ReportController.class;
	}
}
