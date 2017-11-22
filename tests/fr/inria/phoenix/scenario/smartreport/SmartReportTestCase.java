package fr.inria.phoenix.scenario.smartreport;

import static fr.inria.phoenix.diasuite.framework.mocks.Mock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.inria.phoenix.diasuite.framework.mocks.ContactSensorMock;
import fr.inria.phoenix.diasuite.framework.mocks.ElectricMeterMock;
import fr.inria.phoenix.diasuite.framework.mocks.InactivitySensorMock;
import fr.inria.phoenix.diasuite.framework.mocks.MessengerMock;


public class SmartReportTestCase {
	
	
	InactivitySensorMock inactivitySensorMock;
	ContactSensorMock contactSensorMock;
	ElectricMeterMock electricMeterMock;
	MessengerMock messengerMock;
	
	@Before
	public void setUp() throws Exception {
		TIMEOUT=1000;
		underTest(ComponentBinder.class);

		String location = "Target floor";

		inactivitySensorMock = mockInactivitySensor("MockInactivitySensor");
		contactSensorMock  = mockContactSensor("MockContactSensor", location, "Tester");
		electricMeterMock = mockElectricMeter("MockElectricMeter", location, "Tester");
		messengerMock  = mockMessenger("MockMessenger");
	}

	@After
	public void tearDown() throws Exception {
		shutdown();
	}
	
	 @Test
	 public void testSendReport() {
		 inactivitySensorMock.inactivityLevel((float) 2);
		 // Publish a motion value
		 contactSensorMock.contact(true);
		 electricMeterMock.consumption((float)0.5);
		 // And expect a message sent
		 assertTrue(messengerMock.id("id").expectSendMessage());
	}

}
