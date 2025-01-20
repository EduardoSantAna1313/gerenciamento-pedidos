package order

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class DatabaseConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(DockerImageName.parse("postgres:16"))
    }

    @DynamicPropertySource
    fun psqlProperties(registry: DynamicPropertyRegistry, postgresSqlContainer: PostgreSQLContainer<*>) {
        registry.add("spring.datasource.url") { postgresSqlContainer.jdbcUrl }
        registry.add("spring.datasource.username") { postgresSqlContainer.username }
        registry.add("spring.datasource.password") { postgresSqlContainer.password }
    }

}
