package sendMail.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class SimpleMailSender  {    
	/**   
	  * 以文本格式发送邮件   
	  * @param mailInfo 待发送的邮件的信息   
	 * @throws Exception  
	  */    
	    public void sendTextMail(MailSenderInfo mailInfo,String hz,String sffj,String[] fjpath) throws Exception  {    
	    	Properties props = new Properties();
	        props.put("mail.smtp.host", hz);
	        props.put("mail.smtp.auth", "true");
	        //基本的邮件会话
	        Session session = Session.getInstance(props);
	        //构造信息体
	        MimeMessage message = new MimeMessage(session); 
	        //发件地址
	        Address address = new InternetAddress(mailInfo.getFromAddress());
	        message.setFrom(address);
	        //收件地址
	        Address toAddress = new InternetAddress(mailInfo.getToAddress());
	        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
	        
	        //主题
	        message.setSubject(mailInfo.getSubject());
	        	Multipart mp = new MimeMultipart(); 
	        	MimeBodyPart text=new MimeBodyPart();
	        	text.setText(mailInfo.getContent());
	        	mp.addBodyPart(text); 
		        for(int i=0;i<fjpath.length;i++){
		        	MimeBodyPart mbp=new MimeBodyPart(); 
		        	FileDataSource fds=new FileDataSource(fjpath[i]); 
			        mbp.setDataHandler(new DataHandler(fds)); 
			        mbp.setFileName(MimeUtility.encodeText(fds.getName())); 
			        mp.addBodyPart(mbp); 
		        }
		        message.setContent(mp);
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect(hz, mailInfo.getFromAddress(), mailInfo.getPassword());
	        //发送
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();   
	    }  
	    
	    /**   
		  * 以html格式发送邮件   
		  * @param mailInfo 待发送的邮件的信息   
		 * @throws Exception  
		  */    
		    public void sendTextMailhtml(MailSenderInfo mailInfo,String hz,String sffj,String[] fjpath) throws Exception  {    
		    	Properties props = new Properties();
		        props.put("mail.smtp.host", hz);
		        props.put("mail.smtp.auth", "true");
		        //基本的邮件会话
		        Session session = Session.getInstance(props);
		        //构造信息体
		        MimeMessage message = new MimeMessage(session); 
		        //发件地址
		        Address address = new InternetAddress(mailInfo.getFromAddress());
		        message.setFrom(address);
		        //收件地址
		        Address toAddress = new InternetAddress(mailInfo.getToAddress());
		        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
		        
		        //主题
		        message.setSubject(mailInfo.getSubject());
	        	Multipart mp = new MimeMultipart(); 
	        	MimeBodyPart text=new MimeBodyPart();
	        	text.setContent(mailInfo.getContent(),"text/html;charset=gb2312");
	        	mp.addBodyPart(text); 
		        message.setContent(mp);
		        Transport transport = session.getTransport("smtp");
		        transport.connect(hz, mailInfo.getFromAddress(), mailInfo.getPassword());
		        //发送
		        transport.sendMessage(message, message.getAllRecipients());
		        transport.close();   
		    }  
	}   
