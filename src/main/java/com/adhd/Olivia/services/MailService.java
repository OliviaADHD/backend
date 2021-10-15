package com.adhd.Olivia.services;

import org.springframework.stereotype.Service;

import com.adhd.Olivia.models.Mail;



public interface MailService 
{
	public void sendEmail(Mail mail);
}

