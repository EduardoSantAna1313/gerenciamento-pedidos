package br.com.edu.base.mock.extension

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MockRequest(
    val value: Array<String>

)
