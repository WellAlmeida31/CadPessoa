package br.com.pessoa.controller.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.pessoa.modelo.Autenticacao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${jwt.expiration}")
	private String expiration;
	
	@Value("${jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Autenticacao autenticacao = (Autenticacao) authentication.getPrincipal();
		Date hoje = new Date(); 
		Date dataExpiration = new Date(hoje.getTime()+Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API Cad Pessoas")
				.setSubject(autenticacao.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
				
	}

	public boolean isValido(String token) {
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	public Long getIdautenticacao(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		claims.getSubject();
		return Long.parseLong(claims.getSubject());
	}

	
}
