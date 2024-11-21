package br.com.edu.order;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class DatabaseConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer(DockerImageName.parse("postgres:16"));
    }

    @DynamicPropertySource
    void psqlProperties(DynamicPropertyRegistry registry, PostgreSQLContainer<?> postgresSqlContainer) {
        registry.add("spring.datasource.url", postgresSqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresSqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresSqlContainer::getPassword);
    }
}
