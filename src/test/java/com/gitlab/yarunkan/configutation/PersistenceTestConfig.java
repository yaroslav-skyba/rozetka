package com.gitlab.yarunkan.configutation;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import com.gitlab.yarunkan.configuration.AuditorAwareImpl;
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

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(basePackages = {"com.gitlab.yarunkan.repository"})
@PropertySource({"classpath:integration.properties"})
public class PersistenceTestConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment environment) {
        final var entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource(environment));
        entityManagerFactory.setPackagesToScan("com.gitlab.yarunkan.dto");

        final var hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactory.setJpaProperties(getAdditionalProperties(environment));

        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource(Environment environment) {
        final var basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        basicDataSource.setUrl(environment.getProperty("jdbc.url"));
        basicDataSource.setUsername(environment.getProperty("jdbc.user"));
        basicDataSource.setPassword(environment.getProperty("jdbc.pass"));

        return basicDataSource;
    }

    final Properties getAdditionalProperties(Environment environment) {
        final var properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        final var jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
