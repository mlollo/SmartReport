package fr.inria.phoenix.scenario.smartreport.context;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.triggercontext.AbstractTriggerContext;
import fr.inria.phoenix.diasuite.framework.datatype.interaction.Interaction;
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
		Interaction lastInteraction = discover.inactivitySensors().anyOne().getLastInteraction();
		if (inactivityLevelFromInactivitySensor.value() > SmartReportEnabler.getInactivityThreshold()
			&& lastInteraction.getDeviceId().equals(SmartReportEnabler.getTriggerSensorId())
			&& lastInteraction.getActionType().equals(InteractionType.CLOSURE)
		) {
			return new TriggerContextValuePublishable(true, true);
		}
		return new TriggerContextValuePublishable(false, false);
	}

}
