package com.example.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.common.PasswordStorage;

@Configuration
@ComponentScan("com.example.services")
@EnableTransactionManagement
public class AppConfig {
	//=================================== Data ==================================================
	@Bean(name="dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/Security?useLegacyDatetimeCode=false&serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("caingocduong94");
		return dataSource;
	}
	@Bean
	public EntityManager entityManager() {
		return entityManagerFactory().getObject().createEntityManager();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		em.setPackagesToScan("com.example.models");
		return em;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
		// Load property in properties.properties
		rb.setBasenames("properties","application");
		return rb;
	}
	
	// Transaction Manager
	@Bean
	public PlatformTransactionManager transactionManager() {
		final PlatformTransactionManager transactionManager;
		final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		jpaTransactionManager.setDataSource(dataSource());
		transactionManager = jpaTransactionManager;
		
		return transactionManager;
	}
	

	@Bean
	public PasswordStorage passwordEncoder(){
		
		return new PasswordStorage();
	}
}
