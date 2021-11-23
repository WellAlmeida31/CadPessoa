package br.com.pessoa.controller;


import java.net.URI;
import java.net.URL;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pessoa.controller.DTO.CadastroDTO;
import br.com.pessoa.controller.DTO.CepDTO;
import br.com.pessoa.controller.DTO.ConfirmEmailDTO;
import br.com.pessoa.controller.DTO.DetalhesCadastroDto;
import br.com.pessoa.controller.config.validacao.ValidarCpf;
import br.com.pessoa.controller.form.AtualizacaoCadastroForm;
import br.com.pessoa.controller.form.CadastroForm;
import br.com.pessoa.controller.form.formCep;
import br.com.pessoa.controller.service.CadastroRepository;
import br.com.pessoa.controller.service.CadastroService;
import br.com.pessoa.modelo.Cadastro;
import br.com.pessoa.modelo.StatusCadastro;


@RestController
@RequestMapping("/cadPessoa")
public class PessoaController {
	
	
	@Autowired
	private CadastroService cadastroservice;
	
	@Autowired
	private CadastroRepository cadastrorepository;
	

	@Cacheable(value = "listarCadastros")
	@GetMapping
	public Page<CadastroDTO> ListaCadastro(@RequestParam(required = false) String nomePessoa, @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) { 
		if(nomePessoa == null) {
			return CadastroDTO.converter(cadastroservice.listCadastro(paginacao));
		} else
		return CadastroDTO.converter(cadastroservice.listPessoaNome(nomePessoa, paginacao));
		
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listarCadastros", allEntries = true)//usar cache de maneira inteligente, exemplo: usar cache em tabelas que quase nunca sao atualizadas como as que possuem informacoes estaticas como paises e etc..
	public ResponseEntity<CadastroDTO> Cadastrar(@RequestBody @Valid CadastroForm form, UriComponentsBuilder uriBuilder) {
		String cep = form.getCep();
		String cpf = form.getCpf();
		RestTemplate apicep = new RestTemplate();
		Cadastro cadastroform = form.converterErroCPF(cadastrorepository);
		Optional<Cadastro> optional = cadastrorepository.findBycpf(cpf);
		if(optional.isPresent()) {
			cadastroform.setCpf("");
			return  ResponseEntity.internalServerError().body(new CadastroDTO(cadastroform));
		}
		if(!(new ValidarCpf().valida(form.getCpf()))) {
			return ResponseEntity.badRequest().build();
		}
	
		try {
			ResponseEntity<formCep> jsonCep = apicep.exchange("https://viacep.com.br/ws/"+cep+"/json/", HttpMethod.GET, null, formCep.class);
			form = CepDTO.setDadosCEP(form, jsonCep);
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
		//cadastro novo
		cadastroform = form.converter(cadastrorepository);
		cadastroservice.CadastrarCadastro(cadastroform);
		
		//cria a URI do retorno
		URI uri = uriBuilder.path("/cadPessoa/{id}").buildAndExpand(cadastroform.getId()).toUri();
		//retorna o que foi cadastrado na pela requisicao
		return ResponseEntity.created(uri).body(new CadastroDTO(cadastroform));
	}
	
	//busca por id - busca um unico cadastro
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesCadastroDto> detalhar(@PathVariable Long id) {
		Optional<Cadastro> optional = cadastrorepository.findById(id);
		if(optional.isPresent()) {
			Cadastro cadastro = cadastrorepository.getById(id);
			return ResponseEntity.ok(new DetalhesCadastroDto(cadastro));
		}
		return ResponseEntity.notFound().build(); 
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listarCadastros", allEntries = true)
	public ResponseEntity<CadastroDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCadastroForm form){
		String cep = form.getCep();
		
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			JsonNode jsonNodeRoot = objectMapper.readTree(new URL("https://viacep.com.br/ws/"+cep+"/json/"));
			form = CepDTO.setDadosCEPAtualizacao(form, jsonNodeRoot);
			
			Cadastro cadastro = form.atualizar(id,cadastrorepository, form);
			return ResponseEntity.ok(new CadastroDTO(cadastro));
		}catch(Exception e) {
			ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//usar Transactional nas operacoes de salvar, alterar e excluir
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listarCadastros", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Cadastro> optional = cadastrorepository.findById(id);
		if(optional.isPresent()) {
			cadastrorepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@CacheEvict(value = "listarCadastros", allEntries = true)
	@Transactional
	@GetMapping("/confirm/{id}")
	public ResponseEntity<ConfirmEmailDTO> confirmarEmail(@PathVariable Long id) {
		Cadastro cadastro = new Cadastro();
		ConfirmEmailDTO emailDTO = new ConfirmEmailDTO(cadastro);
		try {
			emailDTO.setStatus(StatusCadastro.CADASTRO_APROVADO);
			cadastro = emailDTO.atualizarStatus(id,cadastrorepository, emailDTO);
			cadastroservice.atualizarStatus(cadastro);
			return ResponseEntity.ok(new ConfirmEmailDTO(cadastro));
			
		}catch(Exception e) {
			ResponseEntity.notFound().build();
		}
		
	return ResponseEntity.notFound().build();
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/sort/{pesquisa}")
	public Page<DetalhesCadastroDto> pesquisaCadastro(@PathVariable String pesquisa, Pageable paginacao){
		if(!pesquisa.isEmpty()) {
			Page<Cadastro> cadastro = cadastrorepository.findBynomeContains(pesquisa, paginacao);
			return DetalhesCadastroDto.converter(cadastro);
		}
		return (Page<DetalhesCadastroDto>) ResponseEntity.notFound().build();	
	}
}
