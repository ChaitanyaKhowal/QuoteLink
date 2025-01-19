package com.khowal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(String recipient, String subject, String body) {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email");
        }
		
		return true;
	}

}
