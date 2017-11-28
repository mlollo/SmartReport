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
		String title = new String("Smart Report");
		Contact to = new Contact(Configuration.USER_VALUE, Configuration.USER_EMAIL_VALUE, Configuration.USER_PHONE_VALUE, "", null);
		content.add("Bonjour "+Configuration.USER_VALUE+" !");
		content.add("Voici votre SmartReport : ");

		if (report.value().isEmpty()) {
			content.add("Tout est OK !");
		} else {
			report.value().stream().forEach(p -> {
				String c = "contact" == p.getType() ? " ouvert " : " allumé ";
				content.add("- " + p.getId() + " est" + c);
			});
			
			content.add("\n\nDétail du rapport :");
			report.value().stream().forEach(p -> {
				content.add("- " + p.getId() + " : type [" + p.getType() + "], valeur ["+p.getValue()+"], valeur attendue ["+p.getExpectedValue()+"]");
			});
		}
		Message message = new Message(to, title, content.stream().collect(Collectors.joining("\n")), null);
		System.out.println(message);
		discover.messengers().whereId("MockMessenger").sendMessage(message);
	}
}