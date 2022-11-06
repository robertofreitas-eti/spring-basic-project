package br.eti.freitas.basicproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.ParseException;

import br.eti.freitas.basicproject.service.DataBaseService;

@Configuration
@Profile("devMYSQL")
public class DevEnvironmentConfig {

	@Autowired
	private DataBaseService dataBaseService;
	
	// carrega a variável "strategy" com o informações sobre a estratégia de criação
	// do banco de dados, para verificar a necessidade de carga básica de tabelas
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean iniciarBancoDeDados() throws ParseException {
		
		// Cria o banco e as tabelas
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dataBaseService.iniciarBancoTeste();
		return true;
	}
	
}
