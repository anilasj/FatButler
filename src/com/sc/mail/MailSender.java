package com.sc.mail;

import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sc.util.ConfigProps;


public class MailSender {

	public static void send(String mailServerIP, String mailProtocol, EmailMessage eMsg)
		throws MessagingException, NamingException {
		Session session = getMailSession(mailServerIP, mailProtocol);
		send(session, eMsg);
		
	}
	
	public static void send(EmailMessage eMsg)
		throws MessagingException, NamingException {
		Session session = getMailSession();
		send(session, eMsg);
	
	}
	
	public static void send(Session mailSession, EmailMessage eMsg) 
		throws MessagingException{
		
		try {
			//compose toAddresses
			String[] toAddrs = eMsg.getToAddressList();
			Address[] addresses = new Address[toAddrs.length];
			for (int i=0; i<toAddrs.length;i++)
				addresses[i] = new InternetAddress(toAddrs[i]);
			
			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(eMsg.getFromAddress()));
			msg.setSubject(eMsg.getSubject());
			msg.setSentDate(new java.util.Date());
			msg.addRecipients(Message.RecipientType.TO, addresses);
			if (eMsg.getCcList() != null && eMsg.getCcList().length > 0){
				String[] ccAddrs = eMsg.getToAddressList();
				Address[] ccAddresses = new Address[ccAddrs.length];
				for (int i=0; i<ccAddresses.length;i++)
					ccAddresses[i] = new InternetAddress(ccAddrs[i]);
				msg.addRecipients(Message.RecipientType.CC, ccAddresses);
			}
			
			boolean hasAttachments = false;
			if (eMsg.getAttachFiles() != null && eMsg.getAttachFiles().length >0){ //Adding Attachments
				
				hasAttachments = true;
				Multipart mp = new MimeMultipart();
			
				MimeBodyPart partMsg = new MimeBodyPart();
				partMsg.setText(eMsg.getMessageBody());
				mp.addBodyPart(partMsg);
				
				String[] attachFiles = eMsg.getAttachFiles();
				MimeBodyPart[] partFile = new MimeBodyPart[attachFiles.length];
				for (int i=0; i< attachFiles.length;i++){
					partFile[i] = new MimeBodyPart();
					FileDataSource fs = new FileDataSource(attachFiles[i]);
					partFile[i].setDataHandler(new DataHandler(fs));
					partFile[i].setFileName(fs.getName());
					mp.addBodyPart(partFile[i]);
				}
				msg.setContent(mp);
				
			}
			
			if (eMsg.getAttachImgs() != null && !eMsg.getAttachImgs().isEmpty()){
				
				hasAttachments = true;
				Multipart mp = new MimeMultipart();
				
				MimeBodyPart partMsg = new MimeBodyPart();
				partMsg.setContent(eMsg.getMessageBody(),"text/html");
				mp.addBodyPart(partMsg);
				//add images
				MimeBodyPart[] partFile = new MimeBodyPart[eMsg.getAttachImgs().size()];
				int i= 0;
				Iterator it = eMsg.getAttachImgs().keySet().iterator();
				while (it.hasNext()){
					String imgName = (String) it.next();
					String fileName = (String) eMsg.getAttachImgs().get(imgName);
					partFile[i] = new MimeBodyPart();
					FileDataSource fs = new FileDataSource(fileName);
					//System.out.println("FIle Name--------------" + fileName);
					partFile[i].setDataHandler(new DataHandler(fs));
					partFile[i].setHeader("Content-Type", "image/png" );   
					partFile[i].setHeader("Content-ID","<" +imgName+">");
					partFile[i].setFileName(imgName + ".png");   
					partFile[i].setDisposition("inline");   

					mp.addBodyPart(partFile[i]);
					i++;
				}
				
				msg.setContent(mp);
				
			}
			if (!hasAttachments)
				msg.setText(eMsg.getMessageBody());
			
			Transport.send(msg);
		}catch (Exception ex){
			ex.printStackTrace();
			throw new MessagingException(ex.getMessage());
		}
		
		
	}
		
	private static Session getMailSession() throws NamingException {
		InitialContext ctx = new InitialContext();
		Session mailSession = (Session) ctx.lookup("java:/com/env/mail/MailSession");
		return mailSession;
	}
	
	/**
	 * Use this method when running from standalone apps that do not have access to a JNDI mail session.
	 * 
	 * @return
	 * @throws NamingException
	 */
	private static Session getMailSession(String mailServerIP, String mailProtocol) throws NamingException {
		if (mailProtocol == null || mailServerIP == null)
			throw new NamingException("Mail Properties not initialized!!!");
		Properties props = new Properties();
		props.put("mail." + mailProtocol + ".host", mailServerIP);
		return Session.getDefaultInstance(props,null);
	}
	
	private static Session getMailSessionFromProps()throws NamingException {
		Properties props = new Properties();
		props.put("mail.smtp.host",ConfigProps.getString("mail.smtp.host"));
		props.put("mail.smtp.from", ConfigProps.getString("mail.smtp.from"));
		props.put("mail.smtp.auth", ConfigProps.getString("mail.smtp.auth"));
		props.put("mail.smtp.ssl.enable", ConfigProps.getString("mail.smtp.ssl.enable"));
		props.put("mail.smtp.port", ConfigProps.getString("mail.smtp.port"));
		
		SMTPAuthenticator auth = null;
		
		if (ConfigProps.getString("mail.smtp.auth") != null && ConfigProps.getString("mail.smtp.auth").equals("true")){
			auth= new SMTPAuthenticator();
			auth.setAuthUser(ConfigProps.getString("mail.smtp.user"));
			auth.setAuthPwd(ConfigProps.getString("mail.smtp.password"));
		}
	    Session session = Session.getDefaultInstance(props, auth);
	    return session;
	}
	

}
