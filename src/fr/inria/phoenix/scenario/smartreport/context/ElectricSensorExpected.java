package fr.inria.phoenix.scenario.smartreport.context;

import java.util.Iterator;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.checkexit.CheckExitValue;
import fr.inria.phoenix.diasuite.framework.context.electricsensorexpected.AbstractElectricSensorExpected;
import fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport;
import fr.inria.phoenix.scenario.smartreport.Configuration;

public class ElectricSensorExpected extends AbstractElectricSensorExpected {

	public ElectricSensorExpected(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ElectricSensorExpectedValuePublishable onCheckExit(CheckExitValue checkExitValue,
			DiscoverForCheckExit discover) {
		if (checkExitValue.value() == true) {
			
			Iterator<ElectricMeterProxyForCheckExit> iterator = discover.electricMeters().all().andId(Configuration.CONTACT_SENSOR_KEY).iterator();
			SensorReport report = new SensorReport();
			
			while (iterator.hasNext()) {
				ElectricMeterProxyForCheckExit sensor = iterator.next();
				Float value = sensor.getConsumption();
				if (value > new Float(Configuration.ELECTRIC_SENSOR_VALUE)) {
					report = new SensorReport(new Integer(sensor.id()), Configuration.ELECTRIC_SENSOR_NAME, Configuration.ELECTRIC_SENSOR_VALUE, value.toString());
					return new ElectricSensorExpectedValuePublishable(report, report.getId(), true);
				}
			}
		} 
		return new ElectricSensorExpectedValuePublishable(null, null, false);
	}
}
