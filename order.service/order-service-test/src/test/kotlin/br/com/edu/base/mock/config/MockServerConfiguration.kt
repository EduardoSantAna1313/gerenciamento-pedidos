package br.com.edu.base.mock.config

import br.com.edu.base.mock.MockBaseTest
import org.mockserver.client.MockServerClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration(proxyBeanMethods = false)
open class MockServerConfiguration {

    @Bean
    fun mockServerClient(): MockServerClient {
        val container = MockBaseTest.Companion.mockServerContainer
        return MockServerClient(container.host, container.serverPort)
    }

}