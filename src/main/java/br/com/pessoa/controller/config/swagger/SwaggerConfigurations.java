package br.com.pessoa.controller.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.pessoa.modelo.Autenticacao;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {
	
	
	

	
	@Bean
	public Docket cadPessoaApi(){
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.pessoa"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Autenticacao.class)
                .apiInfo(apiInfo())
                .globalOperationParameters(
                        Arrays.asList(
                                new ParameterBuilder()
                                    .name("Authorization")
                                    .description("Header para Token JWT")
                                    .modelRef(new ModelRef("string"))
                                    .parameterType("header")
                                    .required(false)
                                    .build()));
                
                
                
	}

	
	
	
	
	private ApiInfo apiInfo() {
		 return new ApiInfoBuilder()
		            .title("Cadastro de CEP em Spring Boot REST API - MIRITI Tecnilogia")
		            .description("Texte de consumo de webserver em Spring Boot REST API")
		            .version("1.0.0")
		            .license("Apache License Version 2.0")
		            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
		            .contact(new Contact("Well Almeida", "www.mirititecnologia.com", "wellington31almeida@gmail.com"))
		            .build();
	}
	
}
