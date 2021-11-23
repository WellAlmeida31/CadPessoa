package br.com.pessoa.controller.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.pessoa.modelo.Cadastro;

@Service
public class CadastroService {
	
	@Autowired
	private EmailService emailservice;
	
	@Autowired
	private CadastroRepository cadastrorepository;
	//Pageable paginacao;
	
	public Page<Cadastro> listCadastro(Pageable paginacao) {
		//paginacao = PageRequest.of(pagina, qtd, Direction.ASC, ordenacao);
		return cadastrorepository.findAll(paginacao);
	}
	
	public Page<Cadastro> listPessoaNome(String nomePessoa, Pageable paginacao) {
		//paginacao = PageRequest.of(pagina, qtd);
		return cadastrorepository.findByNome(nomePessoa, paginacao);
	}
	
	public void CadastrarCadastro(Cadastro cadastroform) {
		cadastrorepository.save(cadastroform);
		emailservice.sendOrderConfirmationEmail(cadastroform);
	}
	
	public void atualizarStatus(Cadastro cadastroform) {
		cadastrorepository.save(cadastroform);
	}
	
}
