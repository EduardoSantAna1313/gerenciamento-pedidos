package br.com.edu.wiremock

import br.com.edu.base.wiremock.WireMockBaseTest
import br.com.edu.base.wiremock.WireMockRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.test.assertEquals

class WiremockTest : WireMockBaseTest() {

    @BeforeEach
    fun setup() {
        wireMock.resetAll()
    }

    @Test
    @WireMockRequest(["src/test/resources/wiremock/client-get.json"])
    fun shouldReturn200_on_get() {

        val url = wireMock.url("/clients")

        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder(URI(url))
            .GET()
            .build()
        val result = client.send(request, HttpResponse.BodyHandlers.ofString())

        assertEquals(200, result.statusCode())
    }

    @Test
    @WireMockRequest(["src/test/resources/wiremock/client-post.json"])
    fun shouldReturn201() {

        val url = wireMock.url("/clients")
        val body = """
            {
                "name": "Super Test"   
            }
        """.trimIndent()

        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder(URI(url))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()
        val result = client.send(request, HttpResponse.BodyHandlers.ofString())

        assertEquals(201, result.statusCode())
    }

    @Test
    @WireMockRequest(["src/test/resources/wiremock/product-get.json"])
    fun shouldReturn200_on_get_products() {

        val url = wireMock.url("/products")

        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder(URI(url))
            .GET()
            .build()
        val result = client.send(request, HttpResponse.BodyHandlers.ofString())

        assertEquals(200, result.statusCode())
    }

}
