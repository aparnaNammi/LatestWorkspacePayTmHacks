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

public class OutboundCallToEmergencyService {
	
	final static Logger logger = Logger.getLogger(OutboundCallToEmergencyService.class.getName());
	private static LSSPropertiesLoader lssPropertiesLoader = LSSPropertiesLoader.getInstance();
	public static final String ACCOUNT_SID = lssPropertiesLoader.getValue("outgoingCallToEmerServiceAccountSid");
	
	public static final String AUTH_TOKEN = lssPropertiesLoader.getValue("outgoingCallToEmerServiceAuthToken");

	public static void notifyEmergencyTeam(String name, String mobileNumber) throws URISyntaxException {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		Call call = Call.creator(new PhoneNumber(lssPropertiesLoader.getValue("emergencyContactNumber")),
				new PhoneNumber(lssPropertiesLoader.getValue("outgoingCallNumber")),
				new URI(lssPropertiesLoader.getValue("outgoingCallCheckpoint")+"voice-foremergencyservice-" + mobileNumber + ".xml")).create();

		
		MessageCreator msg = Message.creator(new PhoneNumber(lssPropertiesLoader.getValue("emergencyContactNumber")),
				new PhoneNumber(lssPropertiesLoader.getValue("outgoingCallNumber")),
				"Emergency incident reported for " + name + ". Please respond immediately." +
				"Repeating. Emergency incident reported for " + name + ". Please respond immediately.");
		msg.create();

		
	}

}
