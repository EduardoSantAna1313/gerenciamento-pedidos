package br.com.edu.token

import br.com.edu.order.base.mock.MockBaseTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.JsonBody
import org.mockserver.model.MediaType
import org.springframework.beans.factory.annotation.Autowired
import br.com.edu.token.domain.Token
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals

class TokenServiceTest: MockBaseTest() {

    @Autowired
    private lateinit var tokenInputPort: TokenInputPort

    @BeforeEach
    fun setup() {
        mockClient.reset()
    }

    @Test
    fun `should return the token`() {
        val clientId = UUID.randomUUID().toString()
        val clientSecret = UUID.randomUUID().toString()

        val expectedToken = Token(
            accessToken = UUID.randomUUID().toString(),
            expiresIn = 3600,
            refreshToken = UUID.randomUUID().toString()
        )

        mockClient.`when`(
            HttpRequest.request()
                .withMethod("POST")
                .withPath("/token")
                .withHeader("Content-Type", "application/json")
                .withBody(
                    JsonBody.json("""
                {
                    "client_id": "$clientId",
                    "client_secret": "$clientSecret"
                }
            """.trimIndent()))
        ).respond(
            HttpResponse.response()
                .withStatusCode(201)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody("""
                {
                    "access_token": "${expectedToken.accessToken}",
                    "expires_in": ${expectedToken.expiresIn},
                    "refresh_token": "${expectedToken.refreshToken}"
                }
            """.trimIndent())
        )

        val token = tokenInputPort.generate(clientId, clientSecret)
        assertEquals(expectedToken.accessToken, token.accessToken)
        assertEquals(expectedToken.expiresIn, token.expiresIn)
        assertEquals(expectedToken.refreshToken, token.refreshToken)
    }

    @Test
    fun `should return the 404`() {
        val clientId = UUID.randomUUID().toString()
        val clientSecret = UUID.randomUUID().toString()

        val expectedToken = Token(
            accessToken = UUID.randomUUID().toString(),
            expiresIn = 3600,
            refreshToken = UUID.randomUUID().toString()
        )

        mockClient.`when`(
            HttpRequest.request()
                .withMethod("POST")
                .withPath("/token")
                .withHeader("Content-Type", "application/json")
                .withBody(
                    JsonBody.json("""
                {
                    "client_id": "$clientId",
                    "client_secret": "$clientSecret"
                }
            """.trimIndent()))
        ).respond(
            HttpResponse.response()
                .withStatusCode(201)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody("""
                {
                    "access_token": "${expectedToken.accessToken}",
                    "expires_in": ${expectedToken.expiresIn},
                    "refresh_token": "${expectedToken.refreshToken}"
                }
            """.trimIndent())
        )

        assertThrows<TokenError> { tokenInputPort.generate("bla", "ble") }
    }


}