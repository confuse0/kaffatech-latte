/*
借鉴SendMail类修改而来
 * */
package com.kaffatech.latte.commons.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailInfo {
	private String from = System.getProperty("mailSender");// 发信邮箱
	public InternetAddress[] to;// 收信邮箱
	public String subject = "";// 邮件主题

	private String username = from;
	private String password = "Uban123456";

	public String text = "";// 邮件内容

	private Multipart mp; // 存放邮件的title 内容和附件
	private Properties props; // Properties对象
	private Message message; // Message存储发送的电子邮件信息

	String stmp = "smtp.mxhichina.com";

	public MailInfo() {
		setSmtpHost(this.stmp);
	}

	public MailInfo(String stmp) {
		setSmtpHost(stmp);
	}

	public void setSmtpHost(String smtpHostName) {
		if (props == null) {
			props = new Properties();// 创建Properties对象
		}

		props.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
		props.put("mail.smtp.host", smtpHostName);// 设置发信邮箱的smtp地址"smtp.sina.com"
		props.setProperty("mail.smtp.auth", "true"); // 验证
	}

	public boolean createMimeMessage() {
		Authenticator auth = new AjavaAuthenticator(username, password); // 使用验证，创建一个Authenticator
		Session session = Session.getDefaultInstance(props, auth);// 根据Properties，Authenticator创建Session

		try {
			if (message == null) {
				message = new MimeMessage(session);// Message存储发送的电子邮件信息
			}

			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,to);// 设置收信邮箱
			message.setSubject(subject);// 设置主题
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setOut() {
		try {
			message.setContent(mp);
			message.saveChanges();// add by andy 2012-03-08
			// System.out.println("[INFO] text="+text);
			// message.setText(text);// 设置内容
			System.out.println("[INFO] 开始发送...");
			Transport.send(message);// 发送
			System.out.println("[INFO] 发送完成!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	public boolean setSubject(String mailSubject) {
		System.out.println("set title begin.");
		try {
			if (!mailSubject.equals("") && mailSubject != null) {
				subject = mailSubject;
			}
			return true;
		} catch (Exception e) {
			System.out.println("set Title faild!");
			return false;
		}
	}

	public boolean setFrom(String from) {
		try {
			this.from = from;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean setTo(String to) {
		System.out.println("Set to.");
		if (to == null || to.equals("")) {
			return false;
		}
		try {
			String[] toStrArray = to.split(",");
			this.to = new InternetAddress[toStrArray.length];
			for(int i=0;i<toStrArray.length;i++){
				this.to[i] = new InternetAddress(toStrArray[i]);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean setText(String text) {
		System.out.println("set text begin.");

		try {
			BodyPart bp = new MimeBodyPart();
			// bp.setContent("<meta http-equiv=Context-Type context=text/html;charset=gb2312>"+text,"text/html;charset=GB2312");
			bp.setContent(
					"<meta http-equiv=Context-Type context=text/html;charset=utf-8>"
							+ text, "text/html;charset=UTF-8");
			if (mp == null) {
				mp = new MimeMultipart();
			}

			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			System.out.println("Set context Faild! " + e);
			return false;
		}
	}

	/**
	 * 添加附件..
	 * 
	 * @param filename
	 * @return
	 */
	public boolean addFileAffix(String filename) {
		System.out.println("增加附件..");
		if (mp == null) {
			mp = new MimeMultipart();
		}
		if (filename.equals("") || filename == null) {
			return false;
		}
		String file[];
		file = filename.split(";");
		System.out.println("你有 " + file.length + " 个附件!");

		try {
			for (int i = 0; i < file.length; i++) {
				BodyPart bp = new MimeBodyPart();
				FileDataSource fileds = new FileDataSource(file[i]);
				bp.setDataHandler(new DataHandler(fileds));
				bp.setFileName(fileds.getName());

				mp.addBodyPart(bp);
			}
			return true;
		} catch (Exception e) {
			System.err.println("增加附件: " + filename + "--faild!" + e);
			return false;
		}
	}

	// 创建传入身份验证信息的 Authenticator类
	class AjavaAuthenticator extends Authenticator {
		private String user;
		private String pwd;

		public AjavaAuthenticator(String user, String pwd) {
			this.user = user;
			this.pwd = pwd;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pwd);
		}
	}

}
