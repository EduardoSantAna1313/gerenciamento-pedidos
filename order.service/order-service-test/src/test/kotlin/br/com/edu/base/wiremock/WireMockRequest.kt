package br.com.edu.base.wiremock

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class WireMockRequest(
    val value: Array<String>

)
