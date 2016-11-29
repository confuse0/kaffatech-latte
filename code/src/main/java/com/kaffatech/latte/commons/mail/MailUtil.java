package com.kaffatech.latte.commons.mail;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;

public class MailUtil {

	public static void sendMail(Mail mail) {
		sendMail(mail.getToAddress(), mail.getSubject(), mail.getText());
	}

	public static void sendMail(String toAddress, String subject, String text) {
		if(!StringUtils.isEmpty(toAddress)){
			MailInfo mail = new MailInfo();
			mail.setSubject(subject);
			mail.setText(text);
			mail.setTo(toAddress);
			mail.createMimeMessage();
			mail.setOut();
		}
	}
}
