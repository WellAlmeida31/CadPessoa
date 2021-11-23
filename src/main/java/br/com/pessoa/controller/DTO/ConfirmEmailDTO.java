package br.com.pessoa.controller.DTO;


import br.com.pessoa.controller.service.CadastroRepository;
import br.com.pessoa.modelo.Cadastro;
import br.com.pessoa.modelo.StatusCadastro;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConfirmEmailDTO {

	private StatusCadastro status;

	public ConfirmEmailDTO(Cadastro cadastro) {
		this.status = cadastro.getStatus();
	}

	public Cadastro atualizarStatus(Long id, CadastroRepository cadastrorepository, ConfirmEmailDTO emailDTO) {
		Cadastro cadastro = cadastrorepository.getById(id);
		cadastro.setStatus(emailDTO.getStatus());
		return cadastro;
	}	
}
