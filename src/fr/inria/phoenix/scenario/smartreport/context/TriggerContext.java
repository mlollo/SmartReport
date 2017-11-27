package fr.inria.phoenix.scenario.smartreport.context;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.triggercontext.AbstractTriggerContext;
import fr.inria.phoenix.diasuite.framework.datatype.interactiontype.InteractionType;
import fr.inria.phoenix.diasuite.framework.device.inactivitysensor.InactivityLevelFromInactivitySensor;
import fr.inria.phoenix.scenario.smartreport.SmartReportEnabler;

public class TriggerContext extends AbstractTriggerContext {

	public TriggerContext(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected TriggerContextValuePublishable onInactivityLevelFromInactivitySensor(
			InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor,
			DiscoverForInactivityLevelFromInactivitySensor discover
	) {
		if (Boolean.valueOf(SmartReportEnabler.getAppEnable()) == true
			&& inactivityLevelFromInactivitySensor.value() > Float.valueOf(SmartReportEnabler.getInactivityDuration())
			&& discover.inactivitySensors().anyOne().getLastInteraction().getDeviceId()
			   .equals(SmartReportEnabler.getTriggerSensorId())
			&& discover.inactivitySensors().anyOne().getLastInteraction().getActionType()
			   .equals(Boolean.valueOf(SmartReportEnabler.getTriggerSensorValue()) ? InteractionType.CLOSURE : InteractionType.OPENNING)
		) {
			return new TriggerContextValuePublishable(true, true);
		}
		return new TriggerContextValuePublishable(false, false);
	}

}
