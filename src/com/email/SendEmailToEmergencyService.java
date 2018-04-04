package com.email;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.lsa.util.LSSPropertiesLoader;
import com.pojo.UserProfileDetails;

public class SendEmailToEmergencyService {
	final static Logger logger = Logger.getLogger(SendEmailToEmergencyService.class.getName());
	private static LSSPropertiesLoader lssPropertiesLoader = LSSPropertiesLoader.getInstance();
	public String sendEmailTo108(String mobileNumber, String destinationFile, String urlString, String address, String name, UserProfileDetails userProfileDetails) throws IOException, URISyntaxException {

		logger.info("Emergency team is being contacted.");
		final String toUsername = lssPropertiesLoader.getValue("emergencyMailBox");
		final String fromUsername = lssPropertiesLoader.getValue("sendMailFrom");
		final String password = lssPropertiesLoader.getValue("sendMailPassword");
		String result ="failure";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(fromUsername, password);
					}
				});

		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toUsername));
			/*LifeSaviorDAO connectToDB = new LifeSaviorDAO();
			UserProfileDetails userProfileDetails = null;
			try {
				userProfileDetails = connectToDB.selectData(mobileNumber);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			message.setSubject("Incident Notification for " + name);
			
			MimeBodyPart imgBodyPart = new MimeBodyPart();
			imgBodyPart.attachFile(destinationFile);
			imgBodyPart.setContentID('<'+"i01@example.com"+'>');
			imgBodyPart.setDisposition(MimeBodyPart.INLINE);
			imgBodyPart.setHeader("Content-Type", "image/png");
			
			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(imgBodyPart);
			
			
			MimeBodyPart imgBodyPart1 = new MimeBodyPart();
			if(userProfileDetails!=null){
				imgBodyPart1.setText("Request your immediate assistance for below user:" +
				"\n" +
						"\n" + "Name : " + name +
						"\n" + "Mobile Number : " + userProfileDetails.getMobileNum()+
						"\n" + "Age : " + userProfileDetails.getAge() +
						"\n" + "Critical illness as per the record : " + userProfileDetails.getCriticalIllness()+
						"\n\n" + "Address : " + address+
						"\n\n" + "Map url : " + urlString+
						"\n" + "\n" +
						"\n" );	
			} else {
				imgBodyPart1.setText("Request your immediate assistance for anonymous user:" +
						"\n" +
								"\n" + "Address : " + address+
								"\n\n" + "Map url : " + urlString+
								"\n" + "\n" +
								"\n" );
			}
			
			multipart.addBodyPart(imgBodyPart1);

			//imgBodyPart.setContent(message, "text/html");
			

			message.setContent(multipart);
			Transport.send(message);
			
			result =  "success";
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		logger.info("Emergency team is updated with incident information in Email and SMS.");
		logger.info("Emergency family contacts are also updated about the incident.");
		return result;
	}

}
