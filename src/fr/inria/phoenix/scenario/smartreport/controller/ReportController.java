package fr.inria.phoenix.scenario.smartreport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.reportcontext.ReportContextValue;
import fr.inria.phoenix.diasuite.framework.controller.reportcontroller.AbstractReportController;
import fr.inria.phoenix.diasuite.framework.datatype.contact.Contact;
import fr.inria.phoenix.diasuite.framework.datatype.message.Message;
import fr.inria.phoenix.scenario.smartreport.Configuration;

public class ReportController extends AbstractReportController {

	public ReportController(ServiceConfiguration serviceConfiguration) {
		super(serviceConfiguration);
	}

	@Override
	protected void onReportContext(ReportContextValue report, DiscoverForReportContext discover) {
		List<String> content = new ArrayList<>();
		String title = new String();
		Contact to = new Contact(Configuration.USER_NAME, Configuration.USER_EMAIL, Configuration.USER_PHONE, "", null);
		
		content.add("Smart Report : ");
		report.value().stream().forEach(p -> {
			content.add("sensor : " + p.getName() + " value : " + p.getValue());
		});
		
		Message message = new Message();
		message.setContent(content.stream().collect(Collectors.joining("\n")));
		message.setTitle(title);
		message.setTo(to);
		System.out.println(content);
		discover.messengers().whereId("MockMessenger").sendMessage(message);
	}
}
