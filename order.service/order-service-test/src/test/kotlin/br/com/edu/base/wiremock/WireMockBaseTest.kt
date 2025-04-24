package br.com.edu.base.wiremock

import br.com.edu.base.db.PostgresqlBaseTest
import br.com.edu.base.mock.config.MockServerConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import org.wiremock.integrations.testcontainers.WireMockContainer
import java.time.Duration

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(WireMockPathExtension::class)
abstract class WireMockBaseTest {

    companion object {

        private val DEFAULT_TEST_TAG =
            System.getProperty("wiremock.testcontainer.defaultTag", "3.5.4")

        private val WIREMOCK_DEFAULT_IMAGE: DockerImageName =
            DockerImageName.parse(WireMockContainer.OFFICIAL_IMAGE_NAME).withTag(DEFAULT_TEST_TAG)

        val wiremockServer: WireMockContainer = WireMockContainer(WIREMOCK_DEFAULT_IMAGE)
            .withStartupTimeout(Duration.ofSeconds(60))
            .apply {
                start()
            }

        @RegisterExtension
        val wireMock: WireMockExtension = WireMockExtension
            .newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

        @JvmStatic
        @DynamicPropertySource
        fun setUpProperties(registry: DynamicPropertyRegistry) {
            PostgresqlBaseTest.Companion.setUpPostgresqlProperties(registry)
        }
    }
}