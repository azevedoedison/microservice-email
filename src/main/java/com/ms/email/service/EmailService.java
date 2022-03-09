package com.ms.email.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.email.enums.StatusEmail;
import com.ms.email.model.EmailModel;
import com.ms.email.repository.EmailRepository;

@Service
public class EmailService {
	
	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	private JavaMailSender emailSender;

	@SuppressWarnings("finally")
	public EmailModel sendEmail(EmailModel emailModel) {
		emailModel.setSendDateEmail(LocalDateTime.now());
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailModel.getEmailFrom());
			message.setTo(emailModel.getEmailTo());
			message.setText(emailModel.getText());
			emailSender.send(message);
			
			emailModel.setStatusEmail(StatusEmail.SENT);
		}catch (MailException e) {
			emailModel.setStatusEmail(StatusEmail.ERROR);
		}finally {
			return emailRepository.save(emailModel);
		}
		
	}

}
