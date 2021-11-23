package br.com.pessoa.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TokenDto {

	private String token;
	private String tipo;
	
}