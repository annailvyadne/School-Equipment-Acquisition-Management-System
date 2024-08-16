package MyLibs;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {

	private String FROM = SenderEmail.EmailFrom;
	
	public void sendMail(String to, String subject, String body) {
		 System.out.println("Sending...");
	      
	      Properties properties = new Properties();
	      properties.put("mail.smtp.host", "smtp-mail.outlook.com");
	      properties.put("mail.smtp.port", "587");
	      properties.put("mail.smtp.starttls.enable","true");
	      properties.put("mail.smtp.auth", "true"); 
	      Session session = Session.getInstance(properties, new MyAuthenticator());
	       
	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(FROM));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(body);

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("Sent message successfully.");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
}

class MyAuthenticator extends Authenticator
{
	private String FROM = SenderEmail.EmailFrom;

	MyAuthenticator()
	{
		super();
	}
 
	protected PasswordAuthentication getPasswordAuthentication()
	{
				
		return new PasswordAuthentication(FROM, "group4seam");
	}
}

class SenderEmail {
	public static String EmailFrom = "no-replyseam@outlook.com";
}