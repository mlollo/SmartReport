package fr.inria.phoenix.scenario.smartreport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.inria.diagen.core.enabler.AbstractApplicationEnabler;
import fr.inria.diagen.core.enabler.PropertyFormat;
import fr.inria.diagen.core.enabler.PropertyInfo;

public class SmartReportEnabler extends AbstractApplicationEnabler {
	
	/*Message Receiver Config*/
	public static String userName = Configuration.USER_VALUE;
	public static String userEmail = Configuration.USER_EMAIL_VALUE;
	public static String userPhone = Configuration.USER_PHONE_VALUE;
	
	/*Trigger Sensor Config*/
	public static String triggerSensorId = Configuration.TRIGGER_SENSOR_ID_VALUE;
	public static String inactivityThreshold = Configuration.INACTIVITY_THRESHOLD_VALUE;

	/*Report Sensors Config*/
	public static List<String> contactSensorsId = Arrays.asList(Configuration.CONTACT_SENSORS_ID_VALUE.split(";"));
	public static List<String> contactSensorsValue = Arrays.asList(Configuration.CONTACT_SENSORS_VALUE.split(";"));
	public static List<String> contactSensorsEnable = Arrays.asList(Configuration.CONTACT_SENSORS_ENABLE_VALUE.split(";"));
	
	public static List<String> electricSensorsId = Arrays.asList(Configuration.ELECTRIC_SENSORS_ID_VALUE.split(";"));
	public static List<String> electricSensorsValue = Arrays.asList(Configuration.ELECTRIC_SENSORS_VALUE.split(";"));
	public static List<String> electricSensorsEnable = Arrays.asList(Configuration.ELECTRIC_SENSORS_ENABLE_VALUE.split(";"));
	
	public static List<String> lightSensorsId = Arrays.asList(Configuration.LIGHT_SENSORS_ID_VALUE.split(";"));
	public static List<String> lightSensorsValue = Arrays.asList(Configuration.LIGHT_SENSORS_VALUE.split(";"));
	public static List<String> lightSensorsEnable = Arrays.asList(Configuration.LIGHT_SENSORS_ENABLE_VALUE.split(";"));

	private final static List<PropertyInfo> properties = Arrays.asList(
		/*Message Receiver Config*/
		new PropertyInfo(Configuration.USER_KEY,       Configuration.USER_NAME,       Configuration.USER_DESCRIPTION,       PropertyFormat.Text, Configuration.USER_VALUE),
		new PropertyInfo(Configuration.USER_EMAIL_KEY, Configuration.USER_EMAIL_NAME, Configuration.USER_EMAIL_DESCRIPTION, PropertyFormat.Text, Configuration.USER_EMAIL_VALUE),
		new PropertyInfo(Configuration.USER_PHONE_KEY, Configuration.USER_PHONE_NAME, Configuration.USER_PHONE_DESCRIPTION, PropertyFormat.Text, Configuration.USER_PHONE_VALUE),
		
		/*Trigger Sensor Config*/
		new PropertyInfo(Configuration.TRIGGER_SENSOR_ID_KEY,   Configuration.TRIGGER_SENSOR_ID_NAME,   Configuration.TRIGGER_SENSOR_ID_DESCRIPTION,   PropertyFormat.Text, Configuration.TRIGGER_SENSOR_ID_VALUE),
		new PropertyInfo(Configuration.INACTIVITY_THRESHOLD_KEY, Configuration.INACTIVITY_THRESHOLD_NAME, Configuration.INACTIVITY_THRESHOLD_DESCRIPTION, PropertyFormat.Text, Configuration.INACTIVITY_THRESHOLD_VALUE),
		
		/*Report Sensors Config*/
		new PropertyInfo(Configuration.CONTACT_SENSORS_ID_KEY,      Configuration.CONTACT_SENSORS_ID_NAME,      Configuration.CONTACT_SENSORS_ID_DESCRIPTION,      PropertyFormat.Text, Configuration.CONTACT_SENSORS_ID_VALUE),
		new PropertyInfo(Configuration.CONTACT_SENSORS_KEY,         Configuration.CONTACT_SENSORS_NAME,         Configuration.CONTACT_SENSORS_DESCRIPTION,         PropertyFormat.Text, Configuration.CONTACT_SENSORS_VALUE),
		new PropertyInfo(Configuration.CONTACT_SENSORS_ENABLE_KEY,  Configuration.CONTACT_SENSORS_ENABLE_NAME,  Configuration.CONTACT_SENSORS_ENABLE_DESCRIPTION,  PropertyFormat.Text, Configuration.CONTACT_SENSORS_ENABLE_VALUE),
		
		new PropertyInfo(Configuration.ELECTRIC_SENSORS_ID_KEY,     Configuration.ELECTRIC_SENSORS_ID_NAME,     Configuration.ELECTRIC_SENSORS_ID_DESCRIPTION,     PropertyFormat.Text, Configuration.ELECTRIC_SENSORS_ID_VALUE),
		new PropertyInfo(Configuration.ELECTRIC_SENSORS_KEY,        Configuration.ELECTRIC_SENSORS_NAME,        Configuration.ELECTRIC_SENSORS_DESCRIPTION,        PropertyFormat.Text, Configuration.ELECTRIC_SENSORS_VALUE),
		new PropertyInfo(Configuration.ELECTRIC_SENSORS_ENABLE_KEY, Configuration.ELECTRIC_SENSORS_ENABLE_NAME, Configuration.ELECTRIC_SENSORS_ENABLE_DESCRIPTION, PropertyFormat.Text, Configuration.ELECTRIC_SENSORS_ENABLE_VALUE),
		
		new PropertyInfo(Configuration.LIGHT_SENSORS_ID_KEY,     Configuration.LIGHT_SENSORS_ID_NAME,     Configuration.LIGHT_SENSORS_ID_DESCRIPTION,     PropertyFormat.Text, Configuration.LIGHT_SENSORS_ID_VALUE),
		new PropertyInfo(Configuration.LIGHT_SENSORS_KEY,        Configuration.LIGHT_SENSORS_NAME,        Configuration.LIGHT_SENSORS_DESCRIPTION,        PropertyFormat.Text, Configuration.LIGHT_SENSORS_VALUE),
		new PropertyInfo(Configuration.LIGHT_SENSORS_ENABLE_KEY, Configuration.LIGHT_SENSORS_ENABLE_NAME, Configuration.LIGHT_SENSORS_ENABLE_DESCRIPTION, PropertyFormat.Text, Configuration.LIGHT_SENSORS_ENABLE_VALUE)
	);

	public SmartReportEnabler() {
		super(Configuration.APP_NAME, properties);
	}

	@Override
	public void propertyChanged(String propertyName, String propertyValue) {
		
		/*Message Receiver Config*/
		if (propertyName.equals(Configuration.USER_KEY))
			userName = propertyValue;
		if (propertyName.equals(Configuration.USER_EMAIL_KEY))
			userEmail = propertyValue;
		if (propertyName.equals(Configuration.USER_PHONE_KEY))
			userPhone = propertyValue;
		
		/*Trigger Sensor Config*/
		if (propertyName.equals(Configuration.TRIGGER_SENSOR_ID_KEY))
			triggerSensorId = propertyValue;
		if (propertyName.equals(Configuration.INACTIVITY_THRESHOLD_KEY))
			inactivityThreshold = propertyValue;
		
		/*Report Sensors Config*/
		if (propertyName.equals(Configuration.CONTACT_SENSORS_ID_KEY))
			contactSensorsId = Arrays.asList(propertyValue.split(","));
		if (propertyName.equals(Configuration.CONTACT_SENSORS_KEY))
			contactSensorsValue = Arrays.asList(propertyValue.split(","));
		if (propertyName.equals(Configuration.CONTACT_SENSORS_ENABLE_KEY))
			contactSensorsEnable = Arrays.asList(propertyValue.split(","));
		
		if (propertyName.equals(Configuration.ELECTRIC_SENSORS_ID_KEY))
			electricSensorsId = Arrays.asList(propertyValue.split(","));
		if (propertyName.equals(Configuration.ELECTRIC_SENSORS_KEY))
			electricSensorsValue = Arrays.asList(propertyValue.split(","));
		if (propertyName.equals(Configuration.ELECTRIC_SENSORS_ENABLE_KEY))
			electricSensorsEnable = Arrays.asList(propertyValue.split(","));
		
		if (propertyName.equals(Configuration.LIGHT_SENSORS_ID_KEY))
			lightSensorsId = Arrays.asList(propertyValue.split(","));
		if (propertyName.equals(Configuration.LIGHT_SENSORS_KEY))
			lightSensorsValue = Arrays.asList(propertyValue.split(","));
		if (propertyName.equals(Configuration.LIGHT_SENSORS_ENABLE_KEY))
			lightSensorsEnable = Arrays.asList(propertyValue.split(","));
	}

	@Override
	public boolean isValid(String propertyName, String propertyValue) {
		if (propertyName.equals(Configuration.USER_KEY) 
			|| propertyName.equals(Configuration.USER_EMAIL_KEY) 
			|| propertyName.equals(Configuration.USER_PHONE_KEY) 
			
			|| propertyName.equals(Configuration.TRIGGER_SENSOR_ID_KEY) 
			|| propertyName.equals(Configuration.INACTIVITY_THRESHOLD_KEY) 
			
			|| propertyName.equals(Configuration.CONTACT_SENSORS_ID_KEY) 
			|| propertyName.equals(Configuration.CONTACT_SENSORS_KEY) 
			|| propertyName.equals(Configuration.CONTACT_SENSORS_ENABLE_KEY) 
			
			|| propertyName.equals(Configuration.ELECTRIC_SENSORS_ID_KEY) 
			|| propertyName.equals(Configuration.ELECTRIC_SENSORS_KEY)
			|| propertyName.equals(Configuration.ELECTRIC_SENSORS_ENABLE_KEY)
			
			|| propertyName.equals(Configuration.LIGHT_SENSORS_ID_KEY) 
			|| propertyName.equals(Configuration.LIGHT_SENSORS_KEY)
			|| propertyName.equals(Configuration.LIGHT_SENSORS_ENABLE_KEY)
		) {
			return true;
		}
		return false;
	}
	
	/***
	 * Getter and Setters 
	 * 
	 * Message Receiver Config
	 * ***/
	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		SmartReportEnabler.userName = userName;
	}

	public static String getUserEmail() {
		return userEmail;
	}

	public static void setUserEmail(String userEmail) {
		SmartReportEnabler.userEmail = userEmail;
	}

	public static String getUserPhone() {
		return userPhone;
	}

	public static void setUserPhone(String userPhone) {
		SmartReportEnabler.userPhone = userPhone;
	}

	/***
	 * Getter and Setters 
	 * 
	 * Trigger Sensor Config
	 * ***/
	public static String getTriggerSensorId() {
		return triggerSensorId;
	}

	public static void setTriggerSensorId(String triggerSensorId) {
		SmartReportEnabler.triggerSensorId = triggerSensorId;
	}
	

	public static Float getInactivityThreshold() {
		return Float.valueOf(inactivityThreshold);
	}

	public static void setInactivityThreshold(String inactivityThreshold) {
		SmartReportEnabler.inactivityThreshold = inactivityThreshold;
	}

	/***
	 * Getter and Setters 
	 * 
	 * Report Sensors Config
	 * ***/
	public static List<String> getContactSensorsId() {
		return contactSensorsId;
	}

	public static void setContactSensorsId(List<String> contactSensorsId) {
		SmartReportEnabler.contactSensorsId = contactSensorsId;
	}

	public static List<Boolean> getContactSensorsValue() {
		List<Boolean> res = new ArrayList<>();
		contactSensorsValue.stream().forEach(val -> { res.add(Boolean.valueOf(val)); });
		return res;
	}

	public static void setContactSensorsValue(List<String> contactSensorsValue) {
		SmartReportEnabler.contactSensorsValue = contactSensorsValue;
	}

	public static List<String> getElectricSensorsId() {
		return electricSensorsId;
	}

	public static void setElectricSensorsId(List<String> electricSensorsId) {
		SmartReportEnabler.electricSensorsId = electricSensorsId;
	}

	public static List<Float> getElectricSensorsValue() {
		List<Float> res = new ArrayList<>();
		electricSensorsValue.stream().forEach(val -> { res.add(Float.valueOf(val)); });
		return res;
	}

	public static void setElectricSensorsValue(List<String> electricSensorsValue) {
		SmartReportEnabler.electricSensorsValue = electricSensorsValue;
	}

	public static List<Boolean> getContactSensorsEnable() {
		List<Boolean> res = new ArrayList<>();
		contactSensorsEnable.stream().forEach(val -> { res.add(Boolean.valueOf(val)); });
		return res;
	}

	public static void setContactSensorsEnable(List<String> contactSensorsEnable) {
		SmartReportEnabler.contactSensorsEnable = contactSensorsEnable;
	}

	public static List<Boolean> getElectricSensorsEnable() {
		List<Boolean> res = new ArrayList<>();
		electricSensorsEnable.stream().forEach(val -> { res.add(Boolean.valueOf(val)); });
		return res;
	}

	public static void setElectricSensorsEnable(List<String> electricSensorsEnable) {
		SmartReportEnabler.electricSensorsEnable = electricSensorsEnable;
	}
	
	public static List<String> getLightSensorsId() {
		return lightSensorsId;
	}

	public static void setLightSensorsId(List<String> lightSensorsId) {
		SmartReportEnabler.lightSensorsId = lightSensorsId;
	}

	public static List<Boolean> getLightSensorsValue() {
		List<Boolean> res = new ArrayList<>();
		lightSensorsValue.stream().forEach(val -> { res.add(Boolean.valueOf(val)); });
		return res;
	}

	public static void setLightSensorsValue(List<String> lightSensorsValue) {
		SmartReportEnabler.lightSensorsValue = lightSensorsValue;
	}

	public static List<Boolean> getLightSensorsEnable() {
		List<Boolean> res = new ArrayList<>();
		lightSensorsEnable.stream().forEach(val -> { res.add(Boolean.valueOf(val)); });
		return res;
	}

	public static void setLightSensorsEnable(List<String> lightSensorsEnable) {
		SmartReportEnabler.lightSensorsEnable = lightSensorsEnable;
	}

}
