package com.ojas.hiring.serviceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ojas.hiring.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String toEmail, String subject, String body) {
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("appsupport@ojas.com");
			helper.setTo(toEmail);
			helper.setSubject(subject);
 
			mailSender.send(message);
			System.out.println("Mail sent successfully...");
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception as needed
		}
	}
 
	public void sendEmail(String toEmail, String ccEmail, String subject, String body, boolean isHtml) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("appsupport@ojas.com");
			String[] toEmailArray = toEmail.split(",");
			helper.setTo(toEmailArray);
			if(!ccEmail.isEmpty()) {
				String[] ccEmailArray = ccEmail.split(",");
				helper.setCc(ccEmailArray);
			}
			helper.setSubject(subject);
			helper.setText(body, isHtml);
			mailSender.send(message);
			System.out.println("Mail sent successfully...");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}


