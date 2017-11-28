package fr.inria.phoenix.scenario.smartreport;

public class Configuration {

	public static final String APP_NAME = "SmartReport";

	/***
	 * Configuration de l'application 
	 * ***/
	public static final String APP_ENABLE_KEY = "appEnable";
	public static final String APP_ENABLE_NAME = "Enable Reporting";
	public static final String APP_ENABLE_DESCRIPTION = "Enable the reporting";
	public static final String APP_ENABLE_VALUE = "true";
	
	/***
	 * Configuration de l'utilisateur du device qui recevra les notifications
	 * ***/
	public static final String USER_KEY = "userName";
	public static final String USER_NAME = "User Name";
	public static final String USER_DESCRIPTION = "Name of the user of the smartphone receiver";
	public static final String USER_VALUE = "John";
	
	public static final String USER_EMAIL_KEY = "userEmail";
	public static final String USER_EMAIL_NAME = "User's Email";
	public static final String USER_EMAIL_DESCRIPTION = "Email of the user of the smartphone receiver";
	public static final String USER_EMAIL_VALUE = "test@test.fr";
	
	public static final String USER_PHONE_KEY = "userPhone";
	public static final String USER_PHONE_NAME = "User Mobile Phone";
	public static final String USER_PHONE_DESCRIPTION = "Phone of the user of the smartphone receiver";
	public static final String USER_PHONE_VALUE = "0622334411";
	
	
	/***
	 * Configuration du capteur qui déclenche le smart report
	 * ***/
	public static final String TRIGGER_SENSOR_ID_KEY = "triggerSensorId";
	public static final String TRIGGER_SENSOR_ID_NAME = "Front Door Id";
	public static final String TRIGGER_SENSOR_ID_DESCRIPTION = "Id of the front door";
	public static final String TRIGGER_SENSOR_ID_VALUE = "frontDoorSensor";
	
	public static final String INACTIVITY_THRESHOLD_KEY = "inactivityThresholdValue";
	public static final String INACTIVITY_THRESHOLD_NAME = "Inactivity Threshold";
	public static final String INACTIVITY_THRESHOLD_DESCRIPTION = "How many hour no sensor's detected an interaction before checking the last interaction and sending a report";
	public static final String INACTIVITY_THRESHOLD_VALUE = "0.2";
	
	/***
	 * Configuration des capteurs que l'utilisateur veut dans le smart report
	 * ***/
	public static final String CONTACT_SENSORS_ID_KEY = "contactSensorsId";
	public static final String CONTACT_SENSORS_ID_NAME = "Contact Sensors Id";
	public static final String CONTACT_SENSORS_ID_DESCRIPTION = "List of contact sensors to report";
	public static final String CONTACT_SENSORS_ID_VALUE = "contactSensor1;contactSensor2";
	
	public static final String CONTACT_SENSORS_KEY = "contactSensorsValue";
	public static final String CONTACT_SENSORS_NAME = "Contact Sensors List";
	public static final String CONTACT_SENSORS_DESCRIPTION = "List of expected values";
	public static final String CONTACT_SENSORS_VALUE = "true;false";
	
	public static final String CONTACT_SENSORS_ENABLE_KEY = "contactSensorsEnable";
	public static final String CONTACT_SENSORS_ENABLE_NAME = "Enable Contact Sensors List";
	public static final String CONTACT_SENSORS_ENABLE_DESCRIPTION = "List of enabled contact sensors";
	public static final String CONTACT_SENSORS_ENABLE_VALUE = "true;false";
	
	public static final String ELECTRIC_SENSORS_ID_KEY = "electricSensorsId";
	public static final String ELECTRIC_SENSORS_ID_NAME = "Electric Sensors Id";
	public static final String ELECTRIC_SENSORS_ID_DESCRIPTION = "List of eletric sensors to report";
	public static final String ELECTRIC_SENSORS_ID_VALUE = "electricSensor1;electricSensor2";
	
	public static final String ELECTRIC_SENSORS_KEY = "electricSensorsValue";
	public static final String ELECTRIC_SENSORS_NAME = "Electric Sensors Value";
	public static final String ELECTRIC_SENSORS_DESCRIPTION = "List of expected values";
	public static final String ELECTRIC_SENSORS_VALUE = "0.2;0.35";
	
	public static final String ELECTRIC_SENSORS_ENABLE_KEY = "electricSensorsEnable";
	public static final String ELECTRIC_SENSORS_ENABLE_NAME = "Electric Sensors Enabled";
	public static final String ELECTRIC_SENSORS_ENABLE_DESCRIPTION = "List of enabled eletric sensors";
	public static final String ELECTRIC_SENSORS_ENABLE_VALUE = "true;true";
	
	public static final String LIGHT_SENSORS_ID_KEY = "lightSensorsId";
	public static final String LIGHT_SENSORS_ID_NAME = "Light Sensors Id";
	public static final String LIGHT_SENSORS_ID_DESCRIPTION = "List of light sensors to report";
	public static final String LIGHT_SENSORS_ID_VALUE = "lightSensor1;lightSensor2";
	
	public static final String LIGHT_SENSORS_KEY = "lightSensorsValue";
	public static final String LIGHT_SENSORS_NAME = "Light Sensors Value";
	public static final String LIGHT_SENSORS_DESCRIPTION = "List of expected values";
	public static final String LIGHT_SENSORS_VALUE = "false;false";
	
	public static final String LIGHT_SENSORS_ENABLE_KEY = "lightSensorsEnable";
	public static final String LIGHT_SENSORS_ENABLE_NAME = "Light Sensors Enabled";
	public static final String LIGHT_SENSORS_ENABLE_DESCRIPTION = "List of enabled light sensors";
	public static final String LIGHT_SENSORS_ENABLE_VALUE = "true;true";
}
