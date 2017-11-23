package fr.inria.phoenix.scenario.smartreport.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.reportcontext.AbstractReportContext;
import fr.inria.phoenix.diasuite.framework.datatype.interactiontype.InteractionType;
import fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport;
import fr.inria.phoenix.diasuite.framework.device.inactivitysensor.InactivityLevelFromInactivitySensor;
import fr.inria.phoenix.scenario.smartreport.SmartReportEnabler;

public class ReportContext extends AbstractReportContext {
	
	public ReportContext(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
	}

	@Override
	protected ReportContextValuePublishable onInactivityLevelFromInactivitySensor(
		InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor,
		DiscoverForInactivityLevelFromInactivitySensor discover
	) {
		if (inactivityLevelFromInactivitySensor.value() > new Float(SmartReportEnabler.getInactivityDuration())
			&& discover.inactivitySensors().anyOne().getLastInteraction().getDeviceId()
			   .equals(SmartReportEnabler.getTriggerSensorId())
			&& discover.inactivitySensors().anyOne().getLastInteraction().getActionType()
			   .equals(new Boolean(SmartReportEnabler.getTriggerSensorValue()) ? InteractionType.CLOSURE : InteractionType.OPENNING)
		) {
			List<SensorReport> sensorReports = new ArrayList<>();

			Iterator<ContactSensorProxyForInactivityLevelFromInactivitySensor> iteratorContactSensors = discover.contactSensors().all().iterator();
			Iterator<ElectricMeterProxyForInactivityLevelFromInactivitySensor> iteratorElectricSensors = discover.electricMeters().all().iterator();
			System.out.println(SmartReportEnabler.getContactSensorsId());
			System.out.println(SmartReportEnabler.getElectricSensorsId());

			while (iteratorContactSensors.hasNext()) {
			
				ContactSensorProxyForInactivityLevelFromInactivitySensor sensor = iteratorContactSensors.next();
				int index = SmartReportEnabler.getContactSensorsId().indexOf(sensor.id());
				
				if (index != -1 && index < SmartReportEnabler.getContactSensorsId().size()) {
					String sensorIndex = SmartReportEnabler.getContactSensorsId().get(index);
					String sensorValue = SmartReportEnabler.getContactSensorsValue().get(index);
					
					if (sensor.getContact() != null && sensor.getContact() != Boolean.valueOf(sensorValue))
						sensorReports.add(new SensorReport(sensor.id(), sensorIndex, sensorValue, sensor.getContact().toString()));
				}
			}
					
			while (iteratorElectricSensors.hasNext()) {
			
				ElectricMeterProxyForInactivityLevelFromInactivitySensor sensor = iteratorElectricSensors.next();
				int index = SmartReportEnabler.getElectricSensorsId().indexOf(sensor.id());
				
				if (index != -1 && index < SmartReportEnabler.getElectricSensorsId().size()) {
					String sensorIndex = SmartReportEnabler.getElectricSensorsId().get(index);
					String sensorValue = SmartReportEnabler.getElectricSensorsValue().get(index);
					
					if (sensor.getConsumption() != null && sensor.getConsumption() > new Float(sensorValue))
						sensorReports.add(new SensorReport(sensor.id(), sensorIndex, sensorValue, sensor.getConsumption().toString()));
				}
			}
			return new ReportContextValuePublishable(sensorReports, true);
		}
		return new ReportContextValuePublishable(null, false);
	}
}
