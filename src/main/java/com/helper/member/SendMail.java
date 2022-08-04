package com.helper.member;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendMail {

	public int compare(String mem_id) { // 인증메일 보내기
		// 메일 인코딩
		
		final String bodyEncoding = "UTF-8"; // 콘텐츠 인코딩

		String subject = "StudyHelper 인증번호 요청메일입니다."; // 제목
		String fromEmail = "damnyou1994@gmail.com"; // 보낼 이메일 주소
		String fromUsername = "StudyHelper"; // 보낸사람 이름
		String toEmail = mem_id; // 콤마(,)로 여러개 나열
		int randomNumber = this.ranNum();
		System.out.println(randomNumber);
		final String username = "damnyou1994@gmail.com"; // 구글 계정
		final String password = "ynbaowegjhrjpazf"; // 발급받은 앱 비번

		// 메일에 출력할 텍스트
		StringBuffer sb = new StringBuffer();
//		sb.append("<h2><strong><안녕하세요. StudyHelper입니다.></strong></h4>");
//		sb.append("<h4>해당 인증번호를 입력해 주세요.</h4>");
//		sb.append("<h4>↓↓↓↓↓↓</h4>");
//		sb.append(randomNumber);
	    sb.append("<div style='background-image:url(https://img.freepik.com/premium-photo/teenager-student-girl-over-yellow-wall-pointing-with-the-index-finger-a-great-idea_1368-49455.jpg); width:600px; height: 520px; background-size: cover; border-radius: 100px;'>\r\n"
	    		+ "			<div style=\"font-weight: bolder; padding-top: 20px; padding-left: 85px; font-size: larger;\"><안녕하세요. StudyHelper입니다.></div>\r\n"
	    		+ "			<span style=\"margin-left: 240px;\"></span>\r\n"
	    		+ "			<div style=\"font-weight: bold; margin-bottom: 15px; padding-left: 20px;\"><span style=\"margin-left: 100px;\"></span>해당 인증번호를 입력해 주세요.</div>\r\n"
	    		+ "			<span style=\"margin-left: 270px; margin-top:30px; padding-top:30px;\"></span>			\r\n"
	    		+ randomNumber +"</div>");
		
		String html = sb.toString();
		// 메일 옵션 설정
		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		try {
			// 메일 서버 인증 계정 설정
			// 메일 세션 생성
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                return new javax.mail.PasswordAuthentication(username, password);
	            }
	        });

			// 메일 송/수신 옵션 설정
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, fromUsername));
			message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail, false));
			message.setSubject(subject);
			message.setSentDate(new Date());

			// 메일 콘텐츠 설정
			Multipart mParts = new MimeMultipart();
			MimeBodyPart mTextPart = new MimeBodyPart();
			MimeBodyPart mFilePart = null;

			// 메일 콘텐츠 - 내용
			mTextPart.setText(html, bodyEncoding, "html");
			mParts.addBodyPart(mTextPart);

			// 메일 콘텐츠 설정
			message.setContent(mParts);

			// MIME 타입 설정
			MailcapCommandMap MailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			MailcapCmdMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			MailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			MailcapCmdMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			MailcapCmdMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			MailcapCmdMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(MailcapCmdMap);

			// 메일 발송
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomNumber;
	}

	public int ranNum() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
	
	
}

