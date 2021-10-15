package com.adhd.Olivia.custom;

import com.adhd.Olivia.models.Mail;

public class MailTypes {

	public static Mail signUp() {
		Mail mail = new Mail();
		mail.setMailFrom("sender@gmail.com");
		mail.setMailTo("sattari4@hotmail.com");
		mail.setMailSubject("Registration");
		mail.setMailContent("Confirming successfull registration");
		return mail;
	}
}
