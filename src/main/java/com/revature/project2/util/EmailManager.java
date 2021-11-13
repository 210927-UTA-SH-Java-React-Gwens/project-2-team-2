package com.revature.project2.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public final class EmailManager {
	
	final static String propertyFileTesting = "mailingTest.properties";
	final static String propertyFileProduction = "mailingPro.properties";
	final static String senderEmail = "gwens.list.official.email2@gmail.com";
	final static String senderEmailPassword = "gwenspassword";
	final static String senderEmailTest = "ae6337e8de968c";
	final static String senderEmailPasswordTest = "673b34b6e8dea5";
	static boolean testEnviroment = false;

	
	
	public  boolean sendVerificationCodeMail(String destiny, String subject, String code) {
		Properties prop;
		boolean success = true;
		Session session = null;
		String content = "Please copy the code and submit it on the provided input field after you log in";
		String title = "Welcome to GWENsList!";
		String user;
		String password;
		String html = "<div style=\"background-color: #2B193D; text-align: center; border-radius: 1em;\" >"
				+ "<h1 style=\"text-align: center; color: #C71585;\" >"+ title +" </h1><br/>"
				+ "<p style=\"color: #C71585;\">"+ content +"</p><br/>"
				+ "<p style=\"border:3px; border-style:solid; border-color: #C71585; border-radius: 2em; color: #0197F6; \" > "+ code +"</p></div>"; 
		
		
			try {
				if(testEnviroment) {
					user = senderEmailTest;
					password = senderEmailPasswordTest;
					prop = readPropertiesFile(propertyFileTesting);	
				}	
				else {
					prop = readPropertiesFile(propertyFileProduction);
					user = senderEmail;
					password = senderEmailPassword;
				}
				
				//Sets the Session to the sender mail and sets configurations
				session = Session.getInstance(prop, new Authenticator() {
				    @Override
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication(user, password);
				    }
				});
			}catch (Exception e) {
				e.printStackTrace();
				success = false;
			}
				
				
				
				 try {
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("from@gmail.com"));
		            message.setRecipients(
		                    Message.RecipientType.TO,InternetAddress.parse(destiny));
		            
					message.setSubject(subject);
					message.setContent(html, "text/html; charset=utf-8");

		            Transport.send(message);

		            System.out.println("Done");
		            
		            
				} catch (Exception e) {
					e.printStackTrace();
					success = false;
				}

		
		
		return success;
			
}
	
	
	
	  public  Properties readPropertiesFile(String file) throws IOException {
	      InputStream inputStream = null;
	      Properties prop;
	      try {
	    	 ClassLoader classLoader = getClass().getClassLoader();
	    	 inputStream = classLoader.getResourceAsStream(file);
	         prop = new Properties();
	         prop.load(inputStream);
	      } catch(Exception e) {
	    	  prop = null;
	      } finally {
	         inputStream.close();
	      }
	      return prop;
	   }
	  
	  
	
	
}
