package fr.inria.phoenix.scenario.smartreport.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.reportcontext.AbstractReportContext;
import fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport;
import fr.inria.phoenix.diasuite.framework.device.inactivitysensor.InactivityLevelFromInactivitySensor;
import fr.inria.phoenix.scenario.smartreport.Configuration;

public class ReportContext extends AbstractReportContext {

	public ReportContext(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
	}

	@Override
	protected ReportContextValuePublishable onInactivityLevelFromInactivitySensor(
			InactivityLevelFromInactivitySensor inactivityLevelFromInactivitySensor,
			DiscoverForInactivityLevelFromInactivitySensor discover) {
		List<SensorReport> sensorReports = new ArrayList<>();

		if (inactivityLevelFromInactivitySensor.value() > (float) 1) {
			
			Iterator<ContactSensorProxyForInactivityLevelFromInactivitySensor> iteratorContact = discover.contactSensors().all().andId(Configuration.CONTACT_SENSOR_KEY).iterator();
			while (iteratorContact.hasNext()) {
				ContactSensorProxyForInactivityLevelFromInactivitySensor sensor = iteratorContact.next();
				Boolean value = sensor.getContact();
				if (value != new Boolean(Configuration.CONTACT_SENSOR_VALUE)) {
					sensorReports.add(new SensorReport(new Integer(sensor.id()), Configuration.CONTACT_SENSOR_NAME, Configuration.CONTACT_SENSOR_VALUE, value.toString()));
				}
			}
			
			Iterator<ElectricMeterProxyForInactivityLevelFromInactivitySensor> iteratorEletric = discover.electricMeters().all().andId(Configuration.CONTACT_SENSOR_KEY).iterator();
			while (iteratorEletric.hasNext()) {
				ElectricMeterProxyForInactivityLevelFromInactivitySensor sensor = iteratorEletric.next();
				Float value = sensor.getConsumption();
				if (value > new Float(Configuration.ELECTRIC_SENSOR_VALUE)) {
					sensorReports.add(new SensorReport(new Integer(sensor.id()), Configuration.ELECTRIC_SENSOR_NAME, Configuration.ELECTRIC_SENSOR_VALUE, value.toString()));
				}
			}
		}
		return new ReportContextValuePublishable(sensorReports, !sensorReports.isEmpty());
	}
}
