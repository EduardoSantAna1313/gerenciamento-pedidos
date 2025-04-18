package br.com.edu.order.bootstrap

import br.com.edu.order.domain.Item
import br.com.edu.order.domain.Order
import br.com.edu.order.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.concurrent.Executors
import kotlin.random.Random

//@Component
class Startup (
    val service: OrderService
): CommandLineRunner {

    private val logger = LoggerFactory.getLogger(Startup::class.java)

    override fun run(vararg args: String?) {

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

    fun listItems(max: Int): MutableList<Item> {
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