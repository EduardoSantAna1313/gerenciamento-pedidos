package br.com.edu.base.db.extension

import br.com.edu.base.db.annotations.SqlSetup
import br.com.edu.base.db.annotations.SqlTearDown
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.nio.file.Files
import java.nio.file.Paths

class SqlScriptExtension : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        val jdbcTemplate = SpringExtension.getApplicationContext(context)
            .getBean(JdbcTemplate::class.java)

        val testMethod = context.requiredTestMethod
        val setupAnnotation = testMethod.getAnnotation(SqlSetup::class.java)
            ?: testMethod.declaringClass.getAnnotation(SqlSetup::class.java)

        setupAnnotation?.value?.forEach { path ->
            runSqlScript(jdbcTemplate, path)
        }
    }

    override fun afterEach(context: ExtensionContext) {
        val jdbcTemplate = SpringExtension.getApplicationContext(context)
            .getBean(JdbcTemplate::class.java)

        val testMethod = context.requiredTestMethod
        val tearDownAnnotation = testMethod.getAnnotation(SqlTearDown::class.java)
            ?: testMethod.declaringClass.getAnnotation(SqlTearDown::class.java)

        tearDownAnnotation?.value?.forEach { path ->
            runSqlScript(jdbcTemplate, path)
        }
    }

    private fun runSqlScript(jdbcTemplate: JdbcTemplate, path: String) {
        val script = Files.readString(Paths.get(path))
        jdbcTemplate.execute(script)
    }
}