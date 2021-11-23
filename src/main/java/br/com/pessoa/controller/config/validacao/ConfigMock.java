package br.com.pessoa.controller.config.validacao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.pessoa.controller.service.EmailService;
import br.com.pessoa.controller.service.MockEmailService;
import br.com.pessoa.controller.service.SmtpEmailService;

@Configuration
public class ConfigMock {

	//Mock para envio de email usando a classe MockEmailService
	@Profile(value = "dev")
	@Bean
	public EmailService emailservice() {
		return new MockEmailService();
	}
	
}
