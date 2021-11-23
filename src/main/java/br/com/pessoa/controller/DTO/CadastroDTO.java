package br.com.pessoa.controller.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.pessoa.controller.service.CadastroRepository;
import br.com.pessoa.modelo.Cadastro;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class CadastroDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String cep;
	private String logradouro;
	private LocalDateTime dataCriacao;
	private String localidade;
	private String bairro;
	private String uf;
	private String cpf;
	private String nome;
	private String email;
	
	

	public CadastroDTO(Cadastro cadastro) {
		if(cadastro.getCpf().isBlank()) {
			this.cpf = "Cpf informado já está cadastrado";
			return;
		}
		this.id = cadastro.getId();
		this.cep = cadastro.getCep();
		this.logradouro = cadastro.getLogradouro();
		this.dataCriacao = cadastro.getDataCriacao();
		this.bairro = cadastro.getBairro();
		this.localidade = cadastro.getLocalidade();
		this.uf = cadastro.getUf();
		this.cpf = cadastro.getCpf();
		this.cpf = cadastro.getCpf();
		this.nome = cadastro.getNome();
		this.email = cadastro.getEmail();
	}
	

	public static Page<CadastroDTO> converter(Page<Cadastro> page) {
		return page.map(CadastroDTO::new);
		//page.stream().map(CadastroDTO :: new).collect(Collectors.toList());
	}


	
	
}
