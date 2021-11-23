package br.com.pessoa.controller.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pessoa.modelo.Autenticacao;

public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Long>{

	Optional <Autenticacao> findByusername(String username);
	
}
