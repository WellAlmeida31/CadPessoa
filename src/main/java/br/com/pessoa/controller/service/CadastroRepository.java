package br.com.pessoa.controller.service;

import java.util.Optional;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pessoa.modelo.Cadastro;

public interface CadastroRepository extends JpaRepository<Cadastro, Long>{

	Page<Cadastro> findByNome(String nomePessoa, Pageable paginacao);

	Optional<Cadastro> findBycpf(String cpf);

	Page<Cadastro> findBynomeContains(String nome, Pageable paginacao);
	
}
