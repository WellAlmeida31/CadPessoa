package br.com.pessoa.controller.DTO;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.pessoa.controller.form.AtualizacaoCadastroForm;
import br.com.pessoa.controller.form.CadastroForm;
import br.com.pessoa.controller.form.formCep;
import lombok.Getter;

@Getter
public class CepDTO {

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	

	public static CadastroForm setDadosCEP(CadastroForm form, ResponseEntity<formCep> jsoCep) {	
		form.setBairro(jsoCep.getBody().getBairro());
		form.setComplemento(jsoCep.getBody().getComplemento());
		form.setLocalidade(jsoCep.getBody().getLocalidade());
		form.setLogradouro(jsoCep.getBody().getLogradouro());
		form.setUf(jsoCep.getBody().getUf());
		return form;
	}
	
	public static AtualizacaoCadastroForm setDadosCEPAtualizacao(AtualizacaoCadastroForm form, JsonNode jsonNodeRoot) {
		JsonNode jsNode = jsonNodeRoot.get("bairro");
		form.setBairro(jsNode.asText());
		jsNode = jsonNodeRoot.get("complemento");
		form.setComplemento(jsNode.asText());
		jsNode = jsonNodeRoot.get("localidade");
		form.setLocalidade(jsNode.asText());
		jsNode = jsonNodeRoot.get("logradouro");
		form.setLogradouro(jsNode.asText());
		jsNode = jsonNodeRoot.get("uf");
		form.setUf(jsNode.asText());
		return form;
	}
	
	
}
