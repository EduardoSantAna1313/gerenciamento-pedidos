package br.com.edu.token.domain

import com.fasterxml.jackson.annotation.JsonProperty

class Token(

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("expires_in")
    val expiresIn: Int = 300,

    @JsonProperty("refresh_token")
    val refreshToken: String = ""
)
