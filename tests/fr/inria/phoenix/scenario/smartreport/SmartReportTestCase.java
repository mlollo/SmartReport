package fr.inria.phoenix.scenario.smartreport;

import static fr.inria.phoenix.diasuite.framework.mocks.Mock.*;
import static org.junit.Assert.*;

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
import fr.inria.phoenix.diasuite.framework.mocks.MessengerMock;


public class SmartReportTestCase {
	
	
	InactivitySensorMock inactivitySensorMock;
	ContactSensorMock contactSensor1Mock;
	ContactSensorMock contactSensor2Mock;
	ElectricMeterMock electricMeter1Mock;
	ElectricMeterMock electricMeter2Mock;
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
		messengerMock  = mockMessenger("MockMessenger");
	}

	@After
	public void tearDown() throws Exception {
		shutdown();
	}
	
	 @Test
	 public void testSendReport() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
	
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 
		 // And expect a message sent
		 assertTrue(messengerMock.expectSendMessage());
	}
	 
	 @Test
	 public void testSendReportOneOfEach() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
	
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(true);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.5);
		 
		 // And expect a message sent
		 assertTrue(messengerMock.expectSendMessage());
	}

	 @Test
	 public void testReportOk() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(true);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.1);
		 
		 // And expect a message sent
		 assertFalse(messengerMock.expectSendMessage(new Message(new Contact(), new String(), new String(), null)));
	}
	 
	 @Test
	 public void testReportLastInteraction() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getContactSensorsId().get(0), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 
		 // And expect a message sent
		 assertTrue(!messengerMock.expectSendMessage());
	}
	 
	 
	 @Test
	 public void testReportNoInactivity() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 0.3);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.5);
		 electricMeter2Mock.setConsumption((float)0.5);
		 
		 // And expect a message sent
		 assertTrue(!messengerMock.expectSendMessage());
	}
	 
	 @Test
	 public void testReport() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(false);
		 contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.8);
		 
		 // And expect a message sent
		 assertFalse(messengerMock.expectSendMessage(new Message(new Contact(), new String(), new String() , null)));
	}
	 
	 @Test
	 public void testReportLessSensor() {
		 // Publish a motion value
		 inactivitySensorMock.inactivityLevel((float) 2);
		 inactivitySensorMock.setLastInteraction(new Interaction(InteractionType.CLOSURE, SmartReportEnabler.getTriggerSensorId(), null));
		 
		 // Set a sensor state
		 contactSensor1Mock.setContact(true);
		 //contactSensor2Mock.setContact(false);
		 electricMeter1Mock.setConsumption((float)0.1);
		 electricMeter2Mock.setConsumption((float)0.8);
		 
		 // And expect a message sent
		 assertFalse(messengerMock.expectSendMessage(new Message()));
	}

}
