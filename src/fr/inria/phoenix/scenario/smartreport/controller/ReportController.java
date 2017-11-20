package fr.inria.phoenix.scenario.smartreport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.report.ReportValue;
import fr.inria.phoenix.diasuite.framework.controller.reportcontroller.AbstractReportController;
import fr.inria.phoenix.diasuite.framework.datatype.contact.Contact;
import fr.inria.phoenix.diasuite.framework.datatype.message.Message;
import fr.inria.phoenix.scenario.smartreport.Configuration;

public class ReportController extends AbstractReportController {

	public ReportController(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onReport(ReportValue report, DiscoverForReport discover) {
		List<String> content = new ArrayList<>();
		String title = new String();
		Contact to = new Contact(Configuration.USER_NAME, Configuration.USER_EMAIL, "", "", null);
		
		content.add("Smart Report : ");
		report.value().stream().forEach(p -> {
			content.add("sensor : " + p.getName() + " value : " + p.getValue());
		});
		
		Message message = new Message();
		message.setContent(content.stream().collect(Collectors.joining("\n")));
		message.setTitle(title);
		message.setTo(to);
		discover.messengers().all().sendMessage(message);
	}
	
}
