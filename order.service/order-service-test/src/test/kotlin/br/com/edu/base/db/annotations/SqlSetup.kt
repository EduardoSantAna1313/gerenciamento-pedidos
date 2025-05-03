package br.com.edu.base.db.annotations

import br.com.edu.base.db.extension.SqlScriptExtension
import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ExtendWith(SqlScriptExtension::class)
annotation class SqlSetup(
    val value: Array<String>
)