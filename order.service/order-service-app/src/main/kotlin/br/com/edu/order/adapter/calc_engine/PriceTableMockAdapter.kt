package br.com.edu.order.adapter.calc_engine

import br.com.edu.order.usecases.calc_engine.PriceTablePort
import br.com.edu.order.usecases.calc_engine.PriceTableResult
import org.springframework.stereotype.Component

@Component
class PriceTableMockAdapter : PriceTablePort {
    override fun getPriceTable(numOrder: Long): PriceTableResult {
        return PriceTableResult(
            jsonTabelaCheia = """
                {
                    "table": "full",
                    "order": $numOrder,
                    "random": "${Math.random()}"
                }""".trimIndent(),
            jsonTabelaPraticada = """{"table": "practiced", "order": $numOrder, "random": "${Math.random()}"}"""
        )
    }
}