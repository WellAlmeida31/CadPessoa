package br.com.pessoa.controller;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableAutoConfiguration
@EnableSpringDataWebSupport
@EnableConfigurationProperties
@EntityScan(basePackages = {"br.com.pessoa.modelo"})
@EnableCaching
@EnableSwagger2
public class PessoaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PessoaApplication.class, args);
		
	}

}
