package com.call;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lsa.util.LSSUtil;

public class CreateXml {

	public void createXmlFile(String mobileNumber, String name, String address,
			boolean isMailToEmergencyMedicalTeam, String emergencyContactName) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element rootElement = doc.createElement("Response");
			doc.appendChild(rootElement);

			Element carname = doc.createElement("Say");
			Attr attrType = doc.createAttribute("voice");
			attrType.setValue("Alice");
			carname.setAttributeNode(attrType);
			String fileName = "";
			if (isMailToEmergencyMedicalTeam) {
				carname.appendChild(doc
						.createTextNode("Medical Emergency team, request your immediate assistance for the incident reported for "
								+ name
								+ " at "
								+ address
								+ " area. Request you to look at the email for more details. "
								+ "Repeating. "
								+ "Medical Emergency team, request your immediate assistance for the incident reported for "
								+ name
								+ " at "
								+ address
								+ " area. Request you to look at the email for more details."));
				fileName = "voice-foremergencyservice-" + mobileNumber + ".xml";

			} else {
				carname.appendChild(doc
						.createTextNode(emergencyContactName
								+ ", please be informed that there is an incident reported for "
								+ name
								+ " at "
								+ address
								+ " area. We have informed medical emergency team about the incident. Please act. "
								+ "Repeating. "
								+ emergencyContactName
								+ ", please be informed that there is an incident reported for "
								+ name
								+ " at "
								+ address
								+ " area. We have informed medical emergency team about the incident. Please act."));
				fileName = "voice-foremergencycontact-" + mobileNumber + ".xml";
			}
			rootElement.appendChild(carname);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(LSSUtil.getPath(fileName)));
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
