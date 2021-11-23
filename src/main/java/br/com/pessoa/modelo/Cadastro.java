package br.com.pessoa.modelo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Cadastro {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cep;
	private String logradouro;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@Enumerated(EnumType.STRING)
	private StatusCadastro status = StatusCadastro.AGUARDANDO_APROVACAO;
	private String nome;
	private String bairro;
	private String localidade;
	private String uf;
	private String cpf;
	private String email;

	
	public Cadastro(){
		
	}

	public Cadastro(String cpf){
		this.cpf = cpf;
	}
	
	public Cadastro(String cep, String logradouro, String nome, String bairro, String localidade, String uf, String cpf, String email) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.nome = nome;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
		this.cpf = cpf;
		this.email = email;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cadastro other = (Cadastro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		builder.append("Informações do cadastro: \n");
		builder.append("Cadastro\n cep= ");
		builder.append(getCep());
		builder.append(",\n logradouro= ");
		builder.append(getLogradouro());
		builder.append(",\n dataCriacao= ");
		builder.append(getDataCriacao());
		builder.append(",\n status= ");
		builder.append(getStatus());
		builder.append(",\n nome= ");
		builder.append(getNome());
		builder.append(",\n bairro= ");
		builder.append(getBairro());
		builder.append(",\n localidade= ");
		builder.append(getLocalidade());
		builder.append(",\n uf= ");
		builder.append(getUf());
		builder.append(",\n cpf= ");
		builder.append(getCpf());
		builder.append(",\n Email= ");
		builder.append(getEmail());
		builder.append("\n");
		builder.append("Clique no link abaixo para confirmar o cadastro\n");
		builder.append("http://localhost:8081/cadPessoa/confirm/"+getId()+"");
		return builder.toString();
	}
		

}
