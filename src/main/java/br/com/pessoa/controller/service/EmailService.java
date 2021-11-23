package br.com.pessoa.controller.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.pessoa.modelo.Cadastro;

public interface EmailService {

	void sendOrderConfirmationEmail(Cadastro cadastro);
	
	void sendEmail(SimpleMailMessage msg);
	
}
