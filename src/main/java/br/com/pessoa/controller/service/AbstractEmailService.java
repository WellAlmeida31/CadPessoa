package br.com.pessoa.controller.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.pessoa.modelo.Cadastro;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Cadastro cadastro) {
		SimpleMailMessage smm = prepareSimpleMailMessageFromCadastro(cadastro);
		sendEmail(smm);
		
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromCadastro(Cadastro cadastro) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(cadastro.getEmail());
		smm.setFrom(sender);
		smm.setSubject("Informaçoes do cadastro: "+cadastro.getId());
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText(cadastro.toString());
		return smm;
	}
	
	
}
