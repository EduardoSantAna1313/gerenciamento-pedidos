package br.com.edu.order.service

import br.com.edu.base.db.PostgresqlBaseTest
import br.com.edu.base.db.annotations.SqlSetup
import br.com.edu.base.db.annotations.SqlTearDown
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class OrderServiceBaseDbTest: PostgresqlBaseTest() {

    @Autowired
    private lateinit var service: OrderService

    @Test
    @SqlSetup(["src/test/resources/db/orders-setup.sql"])
    @SqlTearDown(["src/test/resources/db/orders-tear-down.sql"])
    fun shouldSaveBatch() {

        val list = service.list()

        assertEquals(3, list.numberOfElements)
    }

}
