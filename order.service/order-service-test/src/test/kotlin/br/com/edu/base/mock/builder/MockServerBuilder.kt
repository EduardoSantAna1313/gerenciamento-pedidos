package br.com.edu.base.mock.builder

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.JsonBody
import java.io.File
import kotlin.collections.component1
import kotlin.collections.component2

class MockServerBuilder(
    private val mockClient: MockServerClient
) {

    private val mapper = ObjectMapper()

    fun mock(fileName: String) {
        val file = File(fileName)
        val config = mapper.readTree(file)

        val request = createRequestFromFile(config)

        val response = createResponseFromJson(config)

        mockClient.`when`(request).respond(response)
    }

    private fun createRequestFromFile(tree: JsonNode): HttpRequest {

        val requestNode = tree["request"]

        return HttpRequest.request()
            .withMethod(requestNode["method"].asText())
            .withPath(requestNode["path"].asText())
    }

    fun createResponseFromJson(tree: JsonNode): HttpResponse {
        val responseNode = tree["response"]

        val response = HttpResponse.response()
            .withStatusCode(responseNode["statusCode"].asInt())
            .withBody(JsonBody.json(responseNode["body"].asText()))

        responseNode["headers"]?.fields()?.forEach { (name, value) ->
            response.withHeader(Header(name, value.asText()))
        }

        return response
    }

}