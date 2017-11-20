package fr.inria.phoenix.scenario.smartreport.context;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.checkexit.AbstractCheckExit;
import fr.inria.phoenix.diasuite.framework.device.inactivitysensor.InactivityLevelFromInactivitySensor;

public class CheckExit extends AbstractCheckExit {

	public CheckExit(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CheckExitValuePublishable onInactivityLevelFromInactivitySensor(
			  InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor,
			  DiscoverForInactivityLevelFromInactivitySensor discover
			) {
		if (inactivityLevelFromInactivitySensor.value() == (float) 0.1) {
			return new CheckExitValuePublishable(true, true);
		} else {
			return new CheckExitValuePublishable(false, false);
		}
	}

}
