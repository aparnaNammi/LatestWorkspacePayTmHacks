package com.call;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.lsa.util.LSSPropertiesLoader;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class OutboundCallToEmergencyContacts {

	final static Logger logger = Logger
			.getLogger(OutboundCallToEmergencyContacts.class.getName());
	private static LSSPropertiesLoader lssPropertiesLoader = LSSPropertiesLoader.getInstance();

	public static final String ACCOUNT_SID = lssPropertiesLoader.getValue("outgoingCallToEmerContactAccountSid");
	public static final String AUTH_TOKEN = lssPropertiesLoader.getValue("outgoingCallToEmerContactAuthToken");

	public static void notifyEmergencyContact(String name, String mobileNumber,
			String emergencyContactNum) throws URISyntaxException {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Call call = Call
				.creator(
						new PhoneNumber("+91" + emergencyContactNum),
						new PhoneNumber(lssPropertiesLoader.getValue("outgoingCallNumberToEmerContact")),
						new URI(lssPropertiesLoader.getValue("outgoingCallCheckpoint")
								+ "voice-foremergencycontact-" + mobileNumber
								+ ".xml")).create();

		MessageCreator msg = Message.creator(new PhoneNumber("+91"
				+ emergencyContactNum), new PhoneNumber(lssPropertiesLoader.getValue("outgoingCallNumberToEmerContact")),
				"Emergency incident reported for " + name
						+ ". Please respond immediately."
						+ "Repeating. Emergency incident reported for " + name
						+ ". Please respond immediately.");
		msg.create();

	}

}
