package com.adhd.Olivia.custom;

import com.adhd.Olivia.models.Mail;

public class MailTypes {

	public static Mail signUp(String to) {
		Mail mail = new Mail();
		mail.setMailFrom("oliviaapp19@gmail.com");
		mail.setMailTo(to);
		mail.setMailSubject("Confirmation email");
		String body = "Please follow the link to verify your account \n"+ "http://www.somelink.com";
		mail.setMailContent(body);
		return mail;
	}
	
	
	public static Mail resetPassword(String to, String password) {
		Mail mail = new Mail();
		mail.setMailFrom("oliviaapp19@gmail.com");
		mail.setMailTo(to);
		mail.setMailSubject("Reset Password");
		String body = "Please use the following password \n"+ password;
		mail.setMailContent(body);
		return mail;
	}
}
