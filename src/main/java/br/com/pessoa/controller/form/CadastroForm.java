package br.com.pessoa.controller.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.pessoa.controller.service.CadastroRepository;
import br.com.pessoa.modelo.Cadastro;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CadastroForm {
	
	@NotNull @NotEmpty @Length(min = 8, max = 8)
	private String cep;
	
	//@NotNull @NotEmpty @Length(min = 10)
	private String logradouro;
	
	@NotNull @NotEmpty(message = "{name.not.blank}")
	private String nome;
	private String complemento;
	
	//@NotNull
	private String bairro;
	private String localidade;
	private String uf;
	@NotNull @Length(min = 11, max = 11) @CPF(message = "{cpf.not.valid}")
	private String cpf;
	@Email
	private String email;
	
	
	public Cadastro converter( CadastroRepository cadastrorepository) {
		
		return new Cadastro(cep, logradouro, nome, bairro, localidade, uf, cpf, email);
	}
	
	
	public Cadastro converterErroCPF( CadastroRepository cadastrorepository) {
		
		return new Cadastro(cpf);
	}
	
}
