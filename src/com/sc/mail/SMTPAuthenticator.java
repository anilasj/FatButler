package com.sc.mail;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator
{
	String authUser;
	String authPwd;
	
	

    public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}



	public void setAuthPwd(String authPwd) {
		this.authPwd = authPwd;
	}



	public PasswordAuthentication getPasswordAuthentication()
    {
        String username = authUser;
        String password = authPwd;
        return new PasswordAuthentication(username, password);
    }
}
