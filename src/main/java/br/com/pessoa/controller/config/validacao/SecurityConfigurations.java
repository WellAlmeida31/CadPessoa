package br.com.pessoa.controller.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.pessoa.controller.service.AutenticacaoRepository;
import br.com.pessoa.controller.service.AutenticacaoService;
import br.com.pessoa.controller.service.TokenService;

@EnableWebSecurity
@Configuration
@Profile(value = { "default", "prod" })
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private AutenticacaoRepository autenticacaorepository;
	
	@Autowired
	private TokenService tokenservice; 
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//configuracoes de autorizacaoo
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/cadPessoa").permitAll()
		.antMatchers(HttpMethod.GET, "/cadPessoa/**").permitAll()
		.antMatchers(HttpMethod.GET, "/cadPessoa/confirm/**").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		//.antMatchers(HttpMethod.POST, "/login").permitAll()
		
		.antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.DELETE, "/cadPessoa/*").hasRole("ADMINISTRADOR")
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()//em um projeto analisar o metodo de autenticacao para actuator pois esta como permitAll
		.anyRequest().authenticated()
		.and()
		.formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/usuario/pedido", true)
            .permitAll()
        )
		.logout(logout -> {
			logout.logoutUrl("/logout")
				.logoutSuccessUrl("/home");
		})

            
		//.and().formLogin(); //configura um asessao
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoTokenFilter(tokenservice,autenticacaorepository), UsernamePasswordAuthenticationFilter.class);//cria sessao via token stateless
		
	}
	
	
	//configuracaoo de recursos estaticos js css imagens 
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}

	
	
	
	
}
