package com.gitlab.yaroslavskyba.config;

import com.gitlab.yaroslavskyba.AuditorAwareImpl;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(basePackages = {"com.gitlab.yaroslavskyba.repository"})
@PropertySource({"classpath:application.properties"})
public class JpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment environment) {
        final var entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource(environment));
        entityManagerFactory.setPackagesToScan("com.gitlab.yaroslavskyba.model");

        final var hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactory.setJpaProperties(getAdditionalProperties(environment));

        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource(Environment environment) {
        final var basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        basicDataSource.setUrl(System.getenv("ROZETKA_DB_URL"));
        basicDataSource.setUsername(System.getenv("ROZETKA_DB_USER"));
        basicDataSource.setPassword(System.getenv("ROZETKA_DB_PASS"));

        return basicDataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        final var jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    private Properties getAdditionalProperties(Environment environment) {
        final var properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.enable_lazy_load_no_trans", environment.getProperty("hibernate.enable_lazy_load_no_trans"));

        return properties;
    }
}
