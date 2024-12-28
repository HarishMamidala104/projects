package com.wellness.utils;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSender {
	
	public static String sendOtp(String email) {

		boolean flag = false;

		// Email configuration
		String host = "smtp.office365.com";
		String username = "balakrishna.jonnadula@ojas-it.com";
		String password = "npscgtrfndpvwpdk";
		int port = 587;

		// Sender and recipient email addresses
		String senderEmail = "balakrishna.jonnadula@ojas-it.com";
		String recipientEmail = email;

		// Email subject and body
		String subject = "Verification Code";
		String otp = OtpGenerator.getOtp();
		String body = " OTP : " + otp;

		// Set the mail properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);

		// Create a Session object with the authentication credentials
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a MimeMessage object
			MimeMessage message = new MimeMessage(session);

			// Set the sender and recipient addresses
			message.setFrom(new InternetAddress(senderEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			// Set the email subject and body
			message.setSubject(subject);
			message.setText(body);

			// Send the email

			Transport.send(message);
			flag = true;
		} catch (MessagingException e) {
			e.getMessage();
		}

		if (flag) {
			return otp;
		}
		return null;
	}
	
	public static String sendRandomPassword(String email,String passCode) {

		boolean flag = false;

		// Email configuration
		String host = "smtp.office365.com";
		String username = "balakrishna.jonnadula@ojas-it.com";
		String password = "npscgtrfndpvwpdk";
		int port = 587;

		// Sender and recipient email addresses
		String senderEmail = "balakrishna.jonnadula@ojas-it.com";
		String recipientEmail = email;

		// Email subject and body
		String subject = "Password";
		String body = " Password : " + passCode;

		// Set the mail properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);

		// Create a Session object with the authentication credentials
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a MimeMessage object
			MimeMessage message = new MimeMessage(session);

			// Set the sender and recipient addresses
			message.setFrom(new InternetAddress(senderEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			// Set the email subject and body
			message.setSubject(subject);
			message.setText(body);

			// Send the email

			Transport.send(message);
			flag = true;
		} catch (MessagingException e) {
			e.getMessage();
		}

		if (flag) {
			return passCode;
		}
		return null;
	}
}
