package br.com.edu.base.mock.extension

import br.com.edu.base.mock.builder.MockServerBuilder
import org.junit.jupiter.api.extension.*
import org.mockserver.client.MockServerClient

class MockPathExtension : BeforeTestExecutionCallback {

    override fun beforeTestExecution(context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        val annotation = testMethod.getAnnotation(MockRequest::class.java) ?: return

        val mockClient = loadMockServerClient(context.requiredTestInstance)

        val builder = MockServerBuilder(mockClient)
        annotation.value.forEach { path ->
            builder.mock(path)
        }
    }

    private fun loadMockServerClient(testInstance: Any): MockServerClient {

        val mockClientField = testInstance::class.java.fields.first {
            it.type.isAssignableFrom( MockServerClient::class.java)
        }

        mockClientField.isAccessible = true
        return mockClientField.get(testInstance) as MockServerClient
    }

}
