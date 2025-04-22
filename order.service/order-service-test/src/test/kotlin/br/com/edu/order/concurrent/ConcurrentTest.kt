package br.com.edu.order.concurrent

import br.com.edu.base.db.PostgresqlBaseTest
import br.com.edu.order.domain.Item
import br.com.edu.order.domain.Order
import br.com.edu.order.service.OrderService
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.util.concurrent.Executors
import kotlin.random.Random

class ConcurrentTest : PostgresqlBaseTest() {

    private val logger = LoggerFactory.getLogger(ConcurrentTest::class.java)

    @Autowired
    private lateinit var service: OrderService

    @Test
    fun testConcurrent() {
        logger.info("""
            Inciando bootstrap
        """.trimIndent())

        val order = Order()
        order.items = listItems(10)

        val saved = service.save(order)

        val pool = Executors.newVirtualThreadPerTaskExecutor()
        for (i in 1 ..50) {
            pool.submit{
                service.update(saved.id, listItems(i))
            }
        }

        pool.shutdown()

        while (!pool.isTerminated) {
            Thread.sleep(1000)
        }

        val last = service.get(order.id).get()
        logger.info("""
            FIM bootstrap
            $last
            ${last.items.size}
        """.trimIndent())
    }

    private fun listItems(max: Int): MutableList<Item> {
        val list = mutableListOf<Item>()
        for (i in 0..max) {
            val item = Item()
            item.product = "Product-$i"
            item.price = BigDecimal.valueOf(Random.nextDouble())
            list.add(item)
        }
        return list
    }
}