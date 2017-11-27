package fr.inria.phoenix.scenario.smartreport.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.reportcontext.AbstractReportContext;
import fr.inria.phoenix.diasuite.framework.context.triggercontext.TriggerContextValue;
import fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport;
import fr.inria.phoenix.scenario.smartreport.SmartReportEnabler;

public class ReportContext extends AbstractReportContext {
	
	public ReportContext(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
	}

	@Override
	protected ReportContextValuePublishable onTriggerContext(
		TriggerContextValue triggerContextValue,
		DiscoverForTriggerContext discover
	) {
		List<SensorReport> sensorReports = new ArrayList<>();

		Iterator<ContactSensorProxyForTriggerContext> iteratorContactSensors = discover.contactSensors().all().iterator();
		Iterator<ElectricMeterProxyForTriggerContext> iteratorElectricSensors = discover.electricMeters().all().iterator();
		System.out.println(SmartReportEnabler.getContactSensorsId());
		System.out.println(SmartReportEnabler.getElectricSensorsId());

		while (iteratorContactSensors.hasNext()) {
		
			ContactSensorProxyForTriggerContext sensor = iteratorContactSensors.next();
			int index = SmartReportEnabler.getContactSensorsId().indexOf(sensor.id());
			
			if (index != -1 && index < SmartReportEnabler.getContactSensorsId().size()) {
				String sensorValue = SmartReportEnabler.getContactSensorsValue().get(index);
				Boolean sensorEnable = Boolean.valueOf(SmartReportEnabler.getContactSensorsEnable().get(index));
				
				if (sensorEnable == true && sensor.getContact() != null && sensor.getContact() != Boolean.valueOf(sensorValue)) {
					sensorReports.add(new SensorReport(sensor.id(), "contact", sensorValue, sensor.getContact().toString()));
				}
			}
		}
				
		while (iteratorElectricSensors.hasNext()) {
		
			ElectricMeterProxyForTriggerContext sensor = iteratorElectricSensors.next();
			int index = SmartReportEnabler.getElectricSensorsId().indexOf(sensor.id());
			
			if (index != -1 && index < SmartReportEnabler.getElectricSensorsId().size()) {
				String sensorValue = SmartReportEnabler.getElectricSensorsValue().get(index);
				Boolean sensorEnable = Boolean.valueOf(SmartReportEnabler.getElectricSensorsEnable().get(index));

				if (sensorEnable == true && sensor.getConsumption() != null && sensor.getConsumption() > new Float(sensorValue)) {
					sensorReports.add(new SensorReport(sensor.id(), "electric", sensorValue, sensor.getConsumption().toString()));
				}
			}
		}
		return new ReportContextValuePublishable(sensorReports, true);
	}
}
