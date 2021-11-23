package br.com.pessoa.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pessoa.modelo.Autenticacao;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private AutenticacaoRepository autenticacaoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Autenticacao> autenticacao = autenticacaoRepository.findByusername(username);
		if(autenticacao.isPresent()) {
			return autenticacao.get();
		}
		
		throw new UsernameNotFoundException("Nome de usuario ou senha invalidos");	
	}

}
