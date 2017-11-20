package fr.inria.phoenix.scenario.smartreport.context;

import java.util.Iterator;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.checkexit.CheckExitValue;
import fr.inria.phoenix.diasuite.framework.context.contactsensorexpected.AbstractContactSensorExpected;
import fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport;
import fr.inria.phoenix.scenario.smartreport.Configuration;

public class ContactSensorExpected extends AbstractContactSensorExpected {

	public ContactSensorExpected(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ContactSensorExpectedValuePublishable onCheckExit(CheckExitValue checkExitValue,
			  DiscoverForCheckExit discover
			) {
		if (checkExitValue.value() == true) {
			
			Iterator<ContactSensorProxyForCheckExit> iterator = discover.contactSensors().all().andId(Configuration.CONTACT_SENSOR_KEY).iterator();
			SensorReport report = new SensorReport();
			
			while (iterator.hasNext()) {
				ContactSensorProxyForCheckExit sensor = iterator.next();
				Boolean value = sensor.getContact();
				if (value != new Boolean(Configuration.CONTACT_SENSOR_VALUE)) {
					report = new SensorReport(new Integer(sensor.id()), Configuration.CONTACT_SENSOR_NAME, Configuration.CONTACT_SENSOR_VALUE, value.toString());
					return new ContactSensorExpectedValuePublishable(report, report.getId(), true);
				}
			}
		} 
		return new ContactSensorExpectedValuePublishable(null, null, false);
	}

}
