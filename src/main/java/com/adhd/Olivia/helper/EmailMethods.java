package com.adhd.Olivia.helper;


import org.springframework.beans.factory.annotation.Autowired;

import com.adhd.Olivia.models.Mail;
import com.adhd.Olivia.services.MailServiceImpl;

public class EmailMethods {
	
	@Autowired
	private static MailServiceImpl mailMethod;
	
	public static void sendSignUpEmail() {
        Mail mail = new Mail();
        mail.setMailFrom("yashwantchavan@gmail.com");
        mail.setMailTo("sattari4@hotmail.com");
        mail.setMailSubject("Spring Boot - Email Example");
        mail.setMailContent("Learn How to send Email using Spring Boot!!!\n\nThanks\nwww.technicalkeeda.com");

        
        mailMethod.sendEmail(mail);
	}
}
