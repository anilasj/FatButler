package com.sc.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Session;

import com.sc.domain.Task;
import com.sc.domain.User;
import com.sc.mail.EmailMessage;
import com.sc.mail.MailSender;
import com.sc.mail.SMTPAuthenticator;

public class EmailUtil {

	public static Session getMailSessionFromProperties() throws Exception {
		Session session = null;

		Properties props = new Properties();
		PropertyResourceBundle bundle = ConfigProps.getBundle();
		Iterator it = bundle.keySet().iterator();
		while (it.hasNext()){
			String key = (String) it.next();
			if (key.startsWith("mail.smtp.")){
				props.put(key, bundle.getString(key));
			}
		}

		String useAuth = ConfigProps.getString("mail.smtp.auth");
		if (useAuth != null && useAuth.equals("true")){
			SMTPAuthenticator auth = new SMTPAuthenticator();
			auth.setAuthUser(ConfigProps.getString("mail.auth.userid"));
			auth.setAuthPwd(ConfigProps.getString("mail.auth.password"));
			session = Session.getDefaultInstance(props, (Authenticator) auth);
		}else
			session = Session.getDefaultInstance(props, null);

		return session;
	}

	public static void sendAssignInvite(String toEmail, User user, Task task) throws Exception {
		Session session = com.sc.util.EmailUtil.getMailSessionFromProperties();
		EmailMessage msg = new com.sc.mail.EmailMessage();
		msg.setToAddress(toEmail);

		String isTestModeOn = ConfigProps.getString("mail.testMode.on");
		if (isTestModeOn != null && isTestModeOn.equals("true")){
			msg.setToAddress(ConfigProps.getString("mail.testMode.toAddr"));
		}
		msg.setFromAddress(ConfigProps.getString("mail.smtp.from"));

		String subject = ConfigProps.getString("mail.assign.subject");
		MessageFormat msgFormatter = new MessageFormat(subject);
		Object[] values = {Formatter.getTitleCase(user.getName()), task.getName()};
		msg.setSubject(msgFormatter.format(values));
		
		String msgStr = ConfigProps.getString("mail.assign.msg");
		msgFormatter = new MessageFormat(msgStr);
		Object[] mvalues = {Formatter.getTitleCase(user.getName()), task.getName()};
		msgStr = msgFormatter.format(mvalues);
		msg.setMessageBody(msgStr);

		/*HashMap attachFiles = new HashMap();
		String parentImgDir = ConfigProps.getString("upload.parentDir");
		attachFiles.put("logo", parentImgDir + "/" + "images/logo.png");
		
		msg.setAttachImgs(attachFiles);*/
		try {
			MailSender.send(session, msg);
		}catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}

	}
	
}
