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
		List<SensorReport> sensorReports = new ArrayList<>();

		if (inactivityLevelFromInactivitySensor.value() > new Float(SmartReportEnabler.getInactivityDuration())
			&& discover.inactivitySensors().anyOne().getLastInteraction().getDeviceId()
			   .equals(SmartReportEnabler.getTriggerSensorId())
			&& discover.inactivitySensors().anyOne().getLastInteraction().getActionType()
			   .equals(new Boolean(SmartReportEnabler.getTriggerSensorValue()) ? InteractionType.CLOSURE : InteractionType.OPENNING)
		) {
			Iterator<ContactSensorProxyForInactivityLevelFromInactivitySensor> iteratorContactSensors = discover.contactSensors().all().iterator();
			Iterator<ElectricMeterProxyForInactivityLevelFromInactivitySensor> iteratorElectricSensors = discover.electricMeters().all().iterator();

			while (iteratorContactSensors.hasNext()) {
			
				ContactSensorProxyForInactivityLevelFromInactivitySensor sensor = iteratorContactSensors.next();
				System.out.println(SmartReportEnabler.getContactSensorsId());
				int index = SmartReportEnabler.getContactSensorsId().indexOf(sensor.id());
				
				if (index != -1 && index < SmartReportEnabler.getContactSensorsId().size()) {
					String sensorIndex = SmartReportEnabler.getContactSensorsId().get(index);
					String sensorValue = SmartReportEnabler.getContactSensorsValue().get(index);
					System.out.println(sensorIndex);
					System.out.println(sensorValue);
					System.out.println(sensor.getContact());
					
					if (sensor.getContact() != new Boolean(sensorValue)) {
						sensorReports.add(
								new SensorReport(sensor.id(), 
										sensorIndex, 
										sensorValue, 
										sensor.getContact().toString()
										)
								);
					}
				}
			}
					
			while (iteratorElectricSensors.hasNext()) {
			
				ElectricMeterProxyForInactivityLevelFromInactivitySensor sensor = iteratorElectricSensors.next();
				System.out.println(sensor.id());
				System.out.println(SmartReportEnabler.getElectricSensorsId());
				int index = SmartReportEnabler.getElectricSensorsId().indexOf(sensor.id());
				
				if (index != -1 && index < SmartReportEnabler.getElectricSensorsId().size()) {
					String sensorIndex = SmartReportEnabler.getElectricSensorsId().get(index);
					String sensorValue = SmartReportEnabler.getElectricSensorsValue().get(index);
					System.out.println(sensorIndex);
					System.out.println(sensorValue);
					
					if (sensor.getConsumption() > new Float(sensorValue)) {
						sensorReports.add(new SensorReport(sensor.id(), sensorIndex, sensorValue, sensor.getConsumption().toString()));
					}
				}
			}
		}
		return new ReportContextValuePublishable(sensorReports, !sensorReports.isEmpty());
	}
}
