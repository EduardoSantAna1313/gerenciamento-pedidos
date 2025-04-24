package br.com.edu.base.wiremock

import com.github.tomakehurst.wiremock.stubbing.StubImport
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.junit.jupiter.api.extension.*
import java.nio.file.Files
import java.nio.file.Path

class WireMockPathExtension : BeforeTestExecutionCallback {

    override fun beforeTestExecution(context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        val annotation = testMethod.getAnnotation(WireMockRequest::class.java) ?: return

        val mockClient = WireMockBaseTest.wireMock

        mockClient.resetAll()

        annotation.value.forEach { path ->
            val json = Files.readString(Path.of(path))

            val mappings = listOf(StubMapping.buildFrom(json))

            mockClient.importStubs(StubImport(mappings, StubImport.Options.DEFAULTS))
        }
    }

}
