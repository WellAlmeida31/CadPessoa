package br.com.pessoa.controller.config.validacao;

public class ErroFormularioDto {

	private String campo;
	private String mensagemErro;
	
	
	public ErroFormularioDto(String campo, String erro) {
		this.campo = campo;
		this.mensagemErro = erro;
	}


	public String getCampo() {
		return campo;
	}


	public String getErro() {
		return mensagemErro;
	}
	
	
	
}
