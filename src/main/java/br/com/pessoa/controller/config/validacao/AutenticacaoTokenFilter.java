package br.com.pessoa.controller.config.validacao;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pessoa.controller.service.AutenticacaoRepository;
import br.com.pessoa.controller.service.TokenService;
import br.com.pessoa.modelo.Autenticacao;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {
		
	private TokenService tokenservice; 
	private AutenticacaoRepository autenticacaorepository;

	public AutenticacaoTokenFilter(TokenService tokenservice, AutenticacaoRepository autenticacaorepository) {
		this.tokenservice = tokenservice;
		this.autenticacaorepository = autenticacaorepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		boolean valido = tokenservice.isValido(token);
		if (valido) {
			autenticarAcesso(token);
		}
		filterChain.doFilter(request, response);
	}

	private void autenticarAcesso(String token) {
		Long idnome = tokenservice.getIdautenticacao(token);
		Autenticacao autenticacao = autenticacaorepository.findById(idnome).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(autenticacao, null, autenticacao.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token==null||token.isEmpty()||!token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
