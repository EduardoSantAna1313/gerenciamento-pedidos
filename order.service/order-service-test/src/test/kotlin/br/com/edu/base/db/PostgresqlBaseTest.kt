package br.com.edu.base.db

import br.com.edu.OrderServiceApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@SpringBootTest(classes = [OrderServiceApplication::class])
class PostgresqlBaseTest {

    companion object {
        private val POSTGRESQL_CONTAINER = PostgreSQLContainer(DockerImageName.parse("postgres:16"))
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")


        fun setUpPostgresqlProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { POSTGRESQL_CONTAINER.jdbcUrl }
            registry.add("spring.datasource.username") { POSTGRESQL_CONTAINER.username }
            registry.add("spring.datasource.password") { POSTGRESQL_CONTAINER.password }
            registry.add("spring.datasource.driverClassName") { POSTGRESQL_CONTAINER.driverClassName }
            registry.add("spring.cache.local-manager-enabled") { false }
            registry.add("spring.flyway.locations") { "classpath:db/migration/postgresql" }
        }

        init {
            POSTGRESQL_CONTAINER.start()
        }
    }

}