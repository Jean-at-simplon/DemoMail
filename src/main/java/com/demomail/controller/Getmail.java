package com.demomail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demomail.service.Email;

@RestController
@RequestMapping("email")
public class Getmail {
	
	private static final Logger logger = LoggerFactory.getLogger(Getmail.class);

	@Autowired
	Email email;

	@GetMapping("/send")
	public ResponseEntity<?> envoiMail() {
		
		Boolean resultat = null;
		String message;
		String destinataire = "jean.lefrancois@laposte.net";

		String objet = "test d'envoi de mail";

		
		try {
			resultat = email.envoyerMail(destinataire, objet);
			resultat = email.envoyerMail(destinataire, objet);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if (!resultat){
			message = "Le mail n'a pas été envoyé";
		} else {
			message = "le mail a été envoyé";
		}
		return ResponseEntity.ok(message);
	}

}
