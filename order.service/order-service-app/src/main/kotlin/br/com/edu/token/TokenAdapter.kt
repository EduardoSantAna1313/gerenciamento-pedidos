package br.com.edu.token

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import br.com.edu.token.domain.Token
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class TokenAdapter(
    val mapper: ObjectMapper,

    @Value("\${token.url}")
    val baseUrl: String
) : TokenInputPort {

    override fun generate(clientId: String, clientSecret: String): Token {
        val client = HttpClient.newHttpClient()

        val body = """
        {
            "client_id": "$clientId",
            "client_secret": "$clientSecret"
        }
    """.trimIndent()

        val request = HttpRequest.newBuilder(URI("$baseUrl/token"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 201) {
            throw TokenError(response)
        }

        return mapper.readValue(response.body(), Token::class.java)
    }
}

class TokenError(
    response: HttpResponse<String>
): RuntimeException("Erro ao gerar token. Status ${response.statusCode()}. Body: ${response.body()}") {

}