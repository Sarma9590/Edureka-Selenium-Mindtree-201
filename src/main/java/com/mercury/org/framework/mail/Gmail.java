package com.mercury.org.framework.mail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.mercury.org.framework.constants.FrameWorkConstants;
import com.mercury.org.framework.utility.Property;

public class Gmail {
	static String user = null;
	static String password = null;
	static String[] gmailRecepients = null; 
	/**
	 * Initialise Gmail
	 */
	public Gmail() {
		Properties prop = Property.getPropertyInstance(FrameWorkConstants.PROPERTY_FILE_PATH);
		user = Property.getPropertyValue(prop, FrameWorkConstants.GMAIL_USERNAME);
		password = Property.getPropertyValue(prop, FrameWorkConstants.GMAIL_PASSWORD);
		gmailRecepients = Property.getPropertyValue(prop, FrameWorkConstants.GMAIL_RECEPIENTS).split(",", -1);
	}
	/**
	 *  Send Mail With Attachement
	 * @param subject
	 * @param body
	 * @param fileLocationOfAttachement
	 */
	public void sendGMailWithAttachement(String subject, String body, String fileLocationOfAttachement){
		Properties properties = constructGmailProperties();
		Session session = constructGmailSession(properties);
		Address[] recepients = constructGmailAddress(gmailRecepients);
		try {
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
			// Set the from address
			message.setFrom(new InternetAddress(user));
			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO, recepients);
			// Add the subject link
			message.setSubject(subject);
			//To set message body
			BodyPart messageBody = new MimeBodyPart();
			messageBody.setText(body);
			MimeBodyPart attachement = constructGmailAttachement(fileLocationOfAttachement);
			Multipart multipart = constructGmailMultipart(attachement, messageBody);
			// Set Content
			message.setContent(multipart);
			// finally send the email
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constructs Gmail Address format
	 * @param emailAddresses
	 * @return
	 */
	private Address[] constructGmailAddress(String[] emailAddresses){
		 Address[] addresses = new Address[emailAddresses.length];
		for(int i=0; i< emailAddresses.length; i++){
			try {
				addresses[i] = new InternetAddress(emailAddresses[i]);
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}
		return addresses;
	}
	/**
	 * Initialize Gmail Configuration
	 * @return
	 */
	private Properties constructGmailProperties(){
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		return properties;
	}
	/**
	 * Creates Session
	 * @param properties
	 * @return
	 */
	private Session constructGmailSession(Properties properties){
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user,password);
					}
				});
		session.setDebug(true);
		return session;
	}
	/**
	 * Creates Mime Attachment to be send to mail
	 * @param filePath
	 * @return
	 * @throws MessagingException
	 */
	private MimeBodyPart constructGmailAttachement(String filePath) throws MessagingException{
		MimeBodyPart attachement = new MimeBodyPart();
		DataSource source = new FileDataSource(filePath);
		attachement.setDataHandler(new DataHandler(source));
		String lastFileName = null;
		if(filePath.contains("/")){
		String[] filePathSplit = filePath.split("/", -1);
		lastFileName = filePathSplit[filePathSplit.length -1 ];
		}else{
			String[] filePathSplit = filePath.split("\\\\", -1);
			lastFileName = filePathSplit[filePathSplit.length -1 ];
		}
		attachement.setFileName(lastFileName);
		return attachement;
	}
	/**
	 * @param attachement
	 * @param body
	 * @return
	 * @throws MessagingException
	 */
	private Multipart constructGmailMultipart(MimeBodyPart attachement, BodyPart body) throws MessagingException{
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(body);
		multipart.addBodyPart(attachement);
		return multipart; 
	}
}
