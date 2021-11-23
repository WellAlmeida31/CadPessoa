package br.com.pessoa.controller.config.validacao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.pessoa.controller.service.EmailService;
import br.com.pessoa.controller.service.MockEmailService;
import br.com.pessoa.controller.service.SmtpEmailService;

@Configuration
public class Config {

	//Mock para envio de email usando a classe MockEmailService
	/*@Bean
	public EmailService emailservice() {
		return new MockEmailService();
	}*/
	
	@Profile(value = { "default", "prod" })
	@Bean
	public EmailService emailservice() {
		return new SmtpEmailService();
	}
	
}
