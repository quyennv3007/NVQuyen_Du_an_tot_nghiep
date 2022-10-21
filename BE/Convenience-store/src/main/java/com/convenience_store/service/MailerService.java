package com.convenience_store.service;

import javax.mail.MessagingException;

import com.convenience_store.dto.MailInfo;


public interface MailerService {
	void send(MailInfo mail) throws MessagingException;
	void send(String to, String subject, String body) throws MessagingException;

}
