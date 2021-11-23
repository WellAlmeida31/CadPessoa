package br.com.pessoa.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.pessoa.controller.service.CadastroRepository;
import br.com.pessoa.modelo.Cadastro;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AtualizacaoCadastroForm {
	
	@NotNull @NotEmpty @Length(min = 8, max=8)
	private String cep;
	private String logradouro;
	private String localidade;
	private String nome; 
	private String bairro;
	private String uf;
	private String cpf;
	private String complemento;
	private String email;
	

	public Cadastro atualizar(Long id, CadastroRepository cadastrorepository, AtualizacaoCadastroForm form) {
		Cadastro cadastro = cadastrorepository.getOne(id);
		cadastro.setBairro(form.getBairro());
		cadastro.setCep(form.getCep());
		cadastro.setLocalidade(form.getLocalidade());
		cadastro.setLogradouro(form.getLogradouro());
		cadastro.setUf(form.getUf());
		cadastro.setNome(this.nome);
		cadastro.setCpf(this.cpf);
		cadastro.setEmail(this.email);
		return cadastro;
	}
	
}
