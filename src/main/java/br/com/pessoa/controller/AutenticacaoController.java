package br.com.pessoa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pessoa.controller.DTO.TokenDto;
import br.com.pessoa.controller.form.LoginForm;
import br.com.pessoa.controller.service.TokenService;

@RestController
@RequestMapping("/auth")
@Profile(value = { "default", "prod" })
public class AutenticacaoController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken dadoslogin = form.converter();
		try {
			Authentication authentication = authenticationManager.authenticate(dadoslogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
			}
		catch(AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
}
