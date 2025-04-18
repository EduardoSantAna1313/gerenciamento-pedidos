package br.com.edu.token

import br.com.edu.token.domain.Token

interface TokenInputPort {

    fun generate(clientId: String, clientSecret: String): Token

}
