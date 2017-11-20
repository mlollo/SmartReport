package fr.inria.phoenix.scenario.smartreport;

import java.util.Arrays;
import java.util.List;

import fr.inria.diagen.core.enabler.AbstractApplicationEnabler;
import fr.inria.diagen.core.enabler.PropertyFormat;
import fr.inria.diagen.core.enabler.PropertyInfo;

public class SmartReportEnabler extends AbstractApplicationEnabler {

	public static String targetContactSensor = Configuration.CONTACT_SENSOR_VALUE;
	public static String targetElectricSensor = Configuration.ELECTRIC_SENSOR_VALUE;

	private final static List<PropertyInfo> properties = Arrays.asList(
			new PropertyInfo(
						Configuration.CONTACT_SENSOR_KEY, 
						Configuration.CONTACT_SENSOR_NAME, 
						Configuration.CONTACT_SENSOR_DESCRIPTION,
						PropertyFormat.Boolean, 
						Configuration.CONTACT_SENSOR_VALUE
					),
			new PropertyInfo(
						Configuration.ELECTRIC_SENSOR_KEY, 
						Configuration.ELECTRIC_SENSOR_NAME, 
						Configuration.ELECTRIC_SENSOR_DESCRIPTION,
						PropertyFormat.Float, 
						Configuration.ELECTRIC_SENSOR_VALUE
					)
			);

	public SmartReportEnabler() {
		super(Configuration.APP_NAME, properties);
	}

	@Override
	public void propertyChanged(String propertyName, String propertyValue) {
		if (propertyName.equals(Configuration.CONTACT_SENSOR_KEY)) {
			targetContactSensor = propertyValue;
		}
		
		if (propertyName.equals(Configuration.ELECTRIC_SENSOR_KEY)) {
			targetElectricSensor = propertyValue;
		}
	}

	@Override
	public boolean isValid(String propertyName, String propertyValue) {
		if (propertyName.equals(Configuration.CONTACT_SENSOR_KEY)) {
			return true;
		} else if (propertyName.equals(Configuration.ELECTRIC_SENSOR_KEY)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getTargetContactSensor() {
		return targetContactSensor;
	}
	
	public static String getTargetElectricSensor() {
		return targetElectricSensor;
	}
}
