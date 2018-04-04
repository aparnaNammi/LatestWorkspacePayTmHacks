package com.call;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.db.LifeSaviorDAO;
import com.email.SendEmailToEmergencyService;
import com.geolocation.FetchLocationMap;
import com.geolocation.GeoLocationResponse;
import com.geolocation.LocationCoordinates;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.lsa.util.LSSPropertiesLoader;
import com.notification.TrayIconAlert;
import com.pojo.UserProfileDetails;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.CallReader;
import com.twilio.twiml.Say;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

@SuppressWarnings("serial")
@WebServlet("/voice")
public class IncomingCallServlet extends HttpServlet {
	
	private LSSPropertiesLoader lssPropertiesLoader = LSSPropertiesLoader.getInstance();

	final static Logger logger = Logger.getLogger(IncomingCallServlet.class
			.getName());

	// Handle HTTP POST to /voice
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// Step 1 : System Tray alert
		if (SystemTray.isSupported()) {
            TrayIconAlert td = new TrayIconAlert();
            try {
				td.displayTray();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            System.err.println("System tray not supported!");
        }
		// Step 2 : Reading log file
		/*SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new LogReader();
            }
        });*/
		
		// Step 3 : Fetch the incoming call mobile number
		String accountSid = lssPropertiesLoader.getValue("incomingCallAccountSid");
		String authToken = lssPropertiesLoader.getValue("incomingCallAuthToken");
		Twilio.init(accountSid, authToken);

		CallReader call = Call.reader(accountSid);
		String mobileNumber = "";
		ResourceSet<Call> resSet = call.read();
		for (Call call1 : resSet) {
			mobileNumber = call1.getFrom();
			if (mobileNumber.length() >= 10) {
				mobileNumber = mobileNumber.substring(mobileNumber.length() - 10);
			}
			break;
		}
		
		// Step 4 : Connect to DB and fetch user profile details
		logger.info("Incident reported by mobileNumber :" + mobileNumber);
		LifeSaviorDAO connectToDB = new LifeSaviorDAO();
		UserProfileDetails userProfileDetails = null;
		try {
			userProfileDetails = connectToDB.selectData(mobileNumber);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VoiceResponse twiml = null;
		if (userProfileDetails != null
				&& userProfileDetails.getMobileNum() != null) {
			// Step 5 : Respond to user using voice message
			twiml = new VoiceResponse.Builder()
					.say(new Say.Builder(lssPropertiesLoader.getValue("incomingCallvoiceMessageForRegisUser"))
							.voice(Say.Voice.ALICE).build()).build();

			response.setContentType("text/xml");

			try {
				response.getWriter().print(twiml.toXml());
				System.out.println(twiml.toXml());
			} catch (TwiMLException e) {
				e.printStackTrace();
			}
			logger.info("Incident notification received to Life Savior for :"
					+ mobileNumber);
			final String mobNum = mobileNumber;
			final UserProfileDetails userProfileDetails1 = userProfileDetails;
			// Step 6 : Process the flow
			new Thread(){
				public void run(){
			new Thread(){
			    public void run(){
			    	logger.info("Thread Running");
			    	
			      try {
						processFlow(mobNum, userProfileDetails1);
					} catch (InterruptedException | ApiException | URISyntaxException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			  }.start();
			}
			}.start();

			
		} else {
			// Step 7 : Respond to user using voice message
			twiml = new VoiceResponse.Builder()
					.say(new Say.Builder(
							lssPropertiesLoader.getValue("incomingCallvoiceMessageForNonRegisUser"))
							.voice(Say.Voice.ALICE).build()).build();

			response.setContentType("text/xml");

			try {
				response.getWriter().print(twiml.toXml());
				System.out.println(twiml.toXml());
			} catch (TwiMLException e) {
				e.printStackTrace();
			}
			final String mobNum = mobileNumber;
			// Step 8 : Process the flow
						new Thread(){
							public void run(){
						new Thread(){
						    public void run(){
						    	logger.info("Thread Running");
						    	
						      try {
									processFlowForNonRegisteredUser(mobNum);
								} catch (InterruptedException | ApiException | URISyntaxException | IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    }
						  }.start();
						}
						}.start();
		}

	}

	private void processFlow(String mobileNumber,
			UserProfileDetails userProfileDetails) throws IOException,
			InterruptedException, ApiException, URISyntaxException {

		Thread.sleep(10000);

		LocationCoordinates locationCoorMap = setLocationMap(mobileNumber);
		// Step 1 : fetch the location
		FetchLocationMap curlProg = new FetchLocationMap();
		GeoLocationResponse geoLocationResponse = curlProg
				.geolocaton(locationCoorMap);

		Double lat = geoLocationResponse.getLocation().getLat();
		Double lng = geoLocationResponse.getLocation().getLng();
		String destinationFile = "image-"
				+ lat + "-"
				+ lng + ".jpg";

		String urlString = "https://maps.googleapis.com/maps/api/staticmap?"
				+ "center=" + lat + ","
				+ lng
				+ "&size=800x600&sensor=true&zoom=15&maptype=roadmap&"
				+ "markers=color:red%7Clabel:S%7C"
				+ lat + ","
				+ lng;

		// Step 2 : fetch the location
		GeocodingResult geocodingResult = curlProg.getAddress(geoLocationResponse);
		String name = userProfileDetails
				.getFirstName() + " " + userProfileDetails.getMiddleName() + " " +userProfileDetails.getLastName();
		Thread.sleep(10000);
		
		// Step 3 : Send email to emergency team
		SendEmailToEmergencyService sendEmail = new SendEmailToEmergencyService();
		try {
			sendEmail.sendEmailTo108(mobileNumber, destinationFile, urlString,
					geocodingResult.formattedAddress, name, userProfileDetails);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Step 4 : Fetch vicinity of the incident
		String placeVicinity = curlProg.fetchVicinity(geocodingResult.placeId);
		CreateXml createXml = new CreateXml();
		
		// Step 5 : create xml for emergency service
		createXml.createXmlFile(mobileNumber, name, placeVicinity, true, userProfileDetails.getEmergencyContactName1());
		// Step 6 : create xml for emergency contacts name
		createXml.createXmlFile(mobileNumber, name, placeVicinity, false, userProfileDetails.getEmergencyContactName1());
		// Step 7 : Notify emergency team about the incident
		OutboundCallToEmergencyService.notifyEmergencyTeam(name, mobileNumber);
		// Step 8 : Notify emergency contacts about the incident
		OutboundCallToEmergencyContacts.notifyEmergencyContact(name, mobileNumber, userProfileDetails.getEmergencyContactNum1());
	}
	
	private void processFlowForNonRegisteredUser(String mobileNumber) throws IOException,
			InterruptedException, ApiException, URISyntaxException {

		Thread.sleep(10000);

		LocationCoordinates locationCoorMap = setLocationMap(mobileNumber);
		// Step 1 : fetch the location
		FetchLocationMap curlProg = new FetchLocationMap();
		GeoLocationResponse geoLocationResponse = curlProg
				.geolocaton(locationCoorMap);

		Double lat = geoLocationResponse.getLocation().getLat();
		Double lng = geoLocationResponse.getLocation().getLng();
		String destinationFile = "image-"
				+ lat + "-"
				+ lng + ".jpg";

		String urlString = "https://maps.googleapis.com/maps/api/staticmap?"
				+ "center=" + lat + ","
				+ lng
				+ "&size=800x600&sensor=true&zoom=15&maptype=roadmap&"
				+ "markers=color:red%7Clabel:S%7C"
				+ lat + ","
				+ lng;

		// Step 2 : fetch the location
		GeocodingResult geocodingResult = curlProg.getAddress(geoLocationResponse);
		String name = "Anonymous user";
		Thread.sleep(10000);
		
		// Step 3 : Send email to emergency team
		SendEmailToEmergencyService sendEmail = new SendEmailToEmergencyService();
		try {
			sendEmail.sendEmailTo108(mobileNumber, destinationFile, urlString,
					geocodingResult.formattedAddress, name, null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Step 4 : Fetch vicinity of the incident
		String placeVicinity = curlProg.fetchVicinity(geocodingResult.placeId);
		CreateXml createXml = new CreateXml();
		
		// Step 5 : create xml for emergency service
		createXml.createXmlFile(mobileNumber, name, placeVicinity, true, "Anonymous contact");
		// Step 6 : Notify emergency team about the incident
		OutboundCallToEmergencyService.notifyEmergencyTeam(name, mobileNumber);
	}


	/**
	 * @return
	 */
	private LocationCoordinates setLocationMap(String mobileNumber) {
		String properties = lssPropertiesLoader.getValue("mobCoordinates_"+mobileNumber);
		String[] propsArray = properties.split(",");
		LocationCoordinates locationCoordinates = new LocationCoordinates();
		locationCoordinates.setMcc(Integer.parseInt(propsArray[1]));
		locationCoordinates.setMnc(Integer.parseInt(propsArray[2]));
		locationCoordinates.setLac(Integer.parseInt(propsArray[4]));
		if(propsArray[0].equals("gsm")){
			locationCoordinates.setCellId(Integer.parseInt(propsArray[3]));
		} else if(propsArray[0].equals("lte")){
			String[] cellIdArray = propsArray[3].split(" ");
			Integer calcCellId = (Integer.parseInt(cellIdArray[0])) * 256 + Integer.parseInt(cellIdArray[1]);
			locationCoordinates.setCellId(calcCellId);
		}
		return locationCoordinates;
	}
}