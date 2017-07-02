package com.demomail.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import javassist.expr.NewArray;

@Service
public class Email {
	
	private static final Logger logger = LoggerFactory.getLogger(Email.class);
	
	public boolean envoyerMail(String destinataire, String objet) throws Exception {

		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("src/main/resources/javamail.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String mail = properties.getProperty("mail.smtp.from");
		String passe = properties.getProperty("mail.smtp.password");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail, passe);
			}
		});
		
		try {
			prepareAndSendMessage(destinataire, objet, mail, session);

			return true;

		} catch (Exception e) {
			return false;
		}

	}
	
	private void prepareAndSendMessage(String destinataire, String subject, String senderMail, Session session) throws Exception{
		try {
			String statut = "3";
			Date debut = new Date(21/07/2017);
			Date fin = new Date(01/01/2028);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
			message.setSubject(subject);

			MimeBodyPart body = new MimeBodyPart();

			// freemaraker debut de configuration
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
			configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
			configuration.setDefaultEncoding("UTF-8");
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			configuration.setLogTemplateExceptions(false);
						
			Template template = configuration.getTemplate("email"+statut+".ftlh");
						
			Map<String, String> rootMap = new HashMap<String, String>();
			rootMap.put("statut", statut);
			rootMap.put("nom", "LEFRANCOIS");
			rootMap.put("prenom", "Jean");
			rootMap.put("typeConge", "Congé payé");
			rootMap.put("debutConge", "21/07/2017");
			rootMap.put("finConge", "01/01/2028");
			rootMap.put("numDemande", "JEAN00001");
			Writer out = new StringWriter();
			template.process(rootMap, out);
			// freemarker fin de configuration.

			//Insertion du template dans le body
            body.setContent(out.toString(), "text/html;charset=UTF-8");
			
			MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(body);
            
            message.setContent(multipart);

            
            Transport.send(message);


		} catch (MessagingException e) {
			throw new Exception("problème ratp"+e);
		}
	}
}
