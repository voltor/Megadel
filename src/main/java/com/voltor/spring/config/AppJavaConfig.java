package com.voltor.spring.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppJavaConfig {
    
    @Bean(name = "stringPrintWriter") @Scope("prototype")
    public PrintWriter printWriter(){
        // useful when dumping stack trace to file
        return new PrintWriter(new StringWriter());
    }
    
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new org.apache.commons.dbcp.BasicDataSource();
		basicDataSource.setDriverClassName("org.postgresql.Driver");
		basicDataSource.setUrl("jdbc:postgresql://localhost:5432/megadel");
		basicDataSource.setUsername("postgres");
		basicDataSource.setPassword("postgres");
		return basicDataSource;
	} 
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource);
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setPackagesToScan("com.voltor");
		return lef;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();  
		return transactionManager;
	} 
	 

}
