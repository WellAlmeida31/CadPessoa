package br.com.pessoa.controller.DTO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.pessoa.modelo.Cadastro;
import br.com.pessoa.modelo.StatusCadastro;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DetalhesCadastroDto {
	private Long id;
	private String cep;
	private String logradouro;
	private LocalDateTime dataCriacao;
	private String nome;
	private StatusCadastro status;
	private String bairro;
	private String localidade;
	private String uf;
	private String cpf;
	private String email;

	public DetalhesCadastroDto(Cadastro cadastro) {
		this.id = cadastro.getId();
		this.cep = cadastro.getCep();
		this.logradouro = cadastro.getLogradouro();
		this.dataCriacao = cadastro.getDataCriacao();
		this.nome = cadastro.getNome();
		this.bairro = cadastro.getBairro();
		this.status = cadastro.getStatus();
		this.localidade = cadastro.getLocalidade();
		this.uf = cadastro.getUf();
		this.cpf = cadastro.getCpf();
		this.email = cadastro.getEmail();
	}
	
	public static Page<DetalhesCadastroDto> converter(Page<Cadastro> page) {
		return page.map(DetalhesCadastroDto::new);
		//page.stream().map(CadastroDTO :: new).collect(Collectors.toList());
	}

}
