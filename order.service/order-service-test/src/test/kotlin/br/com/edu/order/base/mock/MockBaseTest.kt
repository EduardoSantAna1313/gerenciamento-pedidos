package br.com.edu.order.base.mock

import br.com.edu.order.base.db.PostgresqlBaseTest
import org.mockserver.client.MockServerClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(MockServerConfiguration::class)
abstract class MockBaseTest {

    @Autowired
    lateinit var mockClient: MockServerClient

    companion object {
        val mockServerContainer = MockServerContainer(DockerImageName.parse("mockserver/mockserver")).apply {
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun setUpProperties(registry: DynamicPropertyRegistry) {
            PostgresqlBaseTest.Companion.setUpPostgresqlProperties(registry)
            registry.add("token.url") { "http://${mockServerContainer.host}:${mockServerContainer.serverPort}" }
        }
    }
}