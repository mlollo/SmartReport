package fr.inria.phoenix.scenario.smartreport.context;

import java.util.ArrayList;
import java.util.List;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.contactsensorexpected.ContactSensorExpectedValue;
import fr.inria.phoenix.diasuite.framework.context.electricsensorexpected.ElectricSensorExpectedValue;
import fr.inria.phoenix.diasuite.framework.context.report.AbstractReport;
import fr.inria.phoenix.diasuite.framework.datatype.sensorreport.SensorReport;

public class Report extends AbstractReport {

	public Report(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<SensorReport> onContactSensorExpected(ContactSensorExpectedValue contactSensorExpectedValue) {
		List<SensorReport> sensorReportList = new ArrayList<>();
		SensorReport value = contactSensorExpectedValue.value();
		sensorReportList.add(value);
		//TODO
		return sensorReportList;
	}

	@Override
	protected List<SensorReport> onElectricSensorExpected(ElectricSensorExpectedValue electricSensorExpectedValue) {
		List<SensorReport> sensorReportList = new ArrayList<>();
		SensorReport value = electricSensorExpectedValue.value();
		sensorReportList.add(value);
		//TODO
		return sensorReportList;
	}
}
