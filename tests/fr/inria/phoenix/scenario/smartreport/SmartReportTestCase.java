package fr.inria.phoenix.scenario.smartreport;

import static fr.inria.phoenix.diasuite.framework.mocks.Mock.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.inria.phoenix.diasuite.framework.datatype.contact.Contact;
import fr.inria.phoenix.diasuite.framework.datatype.interaction.Interaction;
import fr.inria.phoenix.diasuite.framework.datatype.interactiontype.InteractionType;
import fr.inria.phoenix.diasuite.framework.datatype.message.Message;
import fr.inria.phoenix.diasuite.framework.mocks.ContactSensorMock;
import fr.inria.phoenix.diasuite.framework.mocks.ElectricMeterMock;
import fr.inria.phoenix.diasuite.framework.mocks.InactivitySensorMock;
import fr.inria.phoenix.diasuite.framework.mocks.LightSensorMock;
import fr.inria.phoenix.diasuite.framework.mocks.MessengerMock;


public class SmartReportTestCase {
	
	
	InactivitySensorMock inactivitySensorMock;
	ContactSensorMock contactSensor1Mock;
	ContactSensorMock contactSensor2Mock;
	ElectricMeterMock electricMeter1Mock;
	ElectricMeterMock electricMeter2Mock;
	LightSensorMock lightSensor1Mock;
	LightSensorMock lightSensor2Mock;
	MessengerMock messengerMock;
	
	@Before
	public void setUp() throws Exception {
		TIMEOUT=1000;
		underTest(ComponentBinder.class);

		String location = "Target floor";

		inactivitySensorMock = mockInactivitySensor(SmartReportEnabler.getTriggerSensorId());
		contactSensor1Mock  = mockContactSensor(SmartReportEnabler.getContactSensorsId().get(0), location, "Tester");
		contactSensor2Mock  = mockContactSensor(SmartReportEnabler.getContactSensorsId().get(1), location, "Tester");
		electricMeter1Mock = mockElectricMeter(SmartReportEnabler.getElectricSensorsId().get(0), location, "Tester");
		electricMeter2Mock = mockElectricMeter(SmartReportEnabler.getElectricSensorsId().get(1), location, "Tester");
		lightSensor1Mock  = mockLightSensor(SmartReportEnabler.getLightSensorsId().get(0), location, "Tester");
		lightSensor2Mock  = mockLightSensor(SmartReportEnabler.getLightSensorsId().get(1), location, "Tester");
		messengerMock  = mockMessenger("MockMessenger");
	}

	@After
	public void tearDown() throws Exception {
		shutdown();
	}
	
	/***
	 * Test unitaire qui vérifie si l'option activer les capteurs fonctionne
	 * ***/
	@Test
	 public void testContactSensorsEnabled() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));
	     
		 
	     // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
	
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(true);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 lightSensor1Mock.setIsAlive(true);
		 lightSensor2Mock.setIsAlive(true);
		 
		 // And expect a message sent
		 assertTrue(messengerMock.expectSendMessage());
	}
	
	/***
	 * Test unitaire qui vérifie si l'option désactiver les capteurs fonctionne
	 * ***/
	@Test
	 public void testContactSensorsDisabled() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("false","false"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("false","false"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("false","false"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

	     // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
	
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(true);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 lightSensor1Mock.setIsAlive(true);
		 lightSensor2Mock.setIsAlive(true);
		 
		 // And expect a message sent
		 assertTrue(messengerMock.expectSendMessage());
	}
	
	/***
	 * Test unitaire qui vérifie le fonctionnement classique du reporting
	 * ***/
	 @Test
	 public void testSendReport() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
	
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(true);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.5);
		 lightSensor1Mock.setIsAlive(true);
		 lightSensor2Mock.setIsAlive(false);
		 
		 // And expect a message sent
		 assertTrue(messengerMock.expectSendMessage());
	}
	 
		
	/***
	 * Test unitaire qui vérifie si un capteur sur deux est dans l'état souhaité
	 * ***/
	 @Test
	 public void testSendReportOneOfEach() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
	
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.5);
		 lightSensor1Mock.setIsAlive(false);
		 lightSensor2Mock.setIsAlive(true);
		 
		 // And expect a message sent
		 assertTrue(messengerMock.expectSendMessage());
	}

		
	/***
	 * Test unitaire qui vérifie si tous les capteurs sont des les états souhaités
	 * ***/
	 @Test
	 public void testReportOk() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(true);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.1);
		 lightSensor1Mock.setIsAlive(false);
		 lightSensor2Mock.setIsAlive(false);
		 
		 // And expect a message sent
		 assertFalse(messengerMock.expectSendMessage(new Message(new Contact(), new String(), new String(), null)));
	}
	 
		
	/***
	 * Test unitaire qui vérifie si la dernière intéraction n'était pas le trigger sensor
	 * ***/
	 @Test
	 public void testReportLastInteraction() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getContactSensorsId().get(0), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(true);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 lightSensor1Mock.setIsAlive(true);
		 lightSensor2Mock.setIsAlive(true);
		 
		 // And expect a message sent
		 assertTrue(!messengerMock.expectSendMessage());
	}
	 
		
	/***
	 * Test unitaire qui vérifie si qu'aucun réporting n'est enclenché si l'inactivity level est infèrieur au délai d'inactivité configurée
	 * ***/
	 @Test
	 public void testReportNoInactivity() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 0.3);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(true);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 lightSensor1Mock.setIsAlive(true);
		 lightSensor2Mock.setIsAlive(true);
		 
		 // And expect a message sent
		 assertTrue(!messengerMock.expectSendMessage());
	}
	 
		
	/***
	 * Test unitaire qui vérifie si des sensor ne renvoie rien, ou ne sont plus alimentés
	 * ***/
	 @Test
	 public void testReportLessSensor() {
	     /*Trigger Sensor*/
	     SmartReportEnabler.setInactivityThreshold("0.5");
	     /*Enable*/
	     SmartReportEnabler.setContactSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setElectricSensorsEnable(Arrays.asList("true","true"));
	     SmartReportEnabler.setLightSensorsEnable(Arrays.asList("true","true"));
	     /*Value*/
	     SmartReportEnabler.setContactSensorsValue(Arrays.asList("true","false"));
	     SmartReportEnabler.setElectricSensorsValue(Arrays.asList("0.2","0.35"));
	     SmartReportEnabler.setLightSensorsValue(Arrays.asList("false","false"));

		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 //contactSensor1Mock.setContact(true);
		 //contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.8);
		 lightSensor1Mock.setIsAlive(true);
		 //lightSensor2Mock.setIsAlive(false);
		 
		 // And expect a message sent
		 assertFalse(messengerMock.expectSendMessage(new Message()));
	}
}
