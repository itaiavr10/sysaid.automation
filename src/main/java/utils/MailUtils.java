package utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import base.LogManager;

public class MailUtils {
	
	 private  String SMTP_HOST = "smtp.gmail.com";
	   private String FROM_ADDRESS = "build@sysaid.com";
	    private String PASSWORD = "*DevOps321_";
	    private String FROM_NAME = "SysAid - Automation";
	    
	    public boolean sendMail(String[] recipients, String[] bccRecipients, String subject, String message) {
	        try {
	            Properties props = new Properties();
	            props.put("mail.smtp.host", SMTP_HOST);
	            props.put("mail.smtp.auth", "true");
	            props.put("mail.debug", "false");
	            props.put("mail.smtp.ssl.enable", "true");
	            props.put("mail.smtp.starttls.enable", "true");

	            javax.mail.Session session = Session.getInstance(props, new SocialAuth());
	            Message msg = new MimeMessage(session);

	            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
	            msg.setFrom(from);

	            InternetAddress[] toAddresses = new InternetAddress[recipients.length];
	            for (int i = 0; i < recipients.length; i++) {
	                toAddresses[i] = new InternetAddress(recipients[i]);
	            }
	            msg.setRecipients(Message.RecipientType.TO, toAddresses);


	            InternetAddress[] bccAddresses = new InternetAddress[bccRecipients.length];
	            for (int j = 0; j < bccRecipients.length; j++) {
	                bccAddresses[j] = new InternetAddress(bccRecipients[j]);
	            }
	            msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
	            // creates message part
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setContent(message, "text/html");

	            // creates multi-part
	            Multipart multipart = new MimeMultipart();
	            multipart.addBodyPart(messageBodyPart);
	            // adds attachments
	            //multipart = addAttachments(multipart);

	            msg.setContent(multipart);

	            msg.setSubject(subject);

	            Transport.send(msg);
	            return true;
	        } catch (UnsupportedEncodingException ex) {
	        	LogManager.error("Send Mail Exception : " +  ex.getMessage());
	            return false;

	        } catch (Exception ex) {
	        	LogManager.error("Send Mail Exception : " +  ex.getMessage());
	            return false;
	        }
	    }
	    
/*	    private Multipart addAttachments(Multipart multipart) throws IOException, MessagingException {

	        String screenShotDirPath = SystemUtils.getProjectPath()+ "/screenShot";
	        File screenShotDir = new File(screenShotDirPath);
	        File[] screenShotList = screenShotDir.listFiles();


	        for (File screenShot : screenShotList){
	            MimeBodyPart attachPart = new MimeBodyPart();
	            attachPart.attachFile(screenShot.getAbsolutePath());
	            multipart.addBodyPart(attachPart);
	        }

	        return multipart;

	    }*/
	    
	    class SocialAuth extends Authenticator {

	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {

	            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

	        }
	    }

}
