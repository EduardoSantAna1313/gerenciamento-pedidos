package simulations

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class OrderSimulation : Simulation() {

    private val httpProtocol = http.baseUrl("http://localhost:8080/v1")
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")

    private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())

    private val createOrder = exec { session ->
        //val numOrder = Random().nextLong(1, 100000)
        val numOrder = 1000000
        session.set("numOrder", numOrder)
    }.exec(
        http("Create Sales Order")
            .post("/sales-orders/#{numOrder}")
            .body(StringBody { session -> createBody(session.getLong("numOrder")) })
            .check(status().`is`(201))
    )

    private fun createBody(numOrder: Long): String {
        val rand = Random()
        val numItems = rand.nextInt(1, 5)
        val items = (1..numItems).map {
            SalesOrderRequest(
                numItem = it,
                productId = "Product $it",
                price = BigDecimal.valueOf(rand.nextDouble(10.0, 100.0)).setScale(2, RoundingMode.HALF_UP),
                quantity = rand.nextInt(1, 10),
                createdBy = "Gatling"
            )
        }
        val json = objectMapper.writeValueAsString(items)
        //println(json)
        return json
    }

    private val scn = scenario("Sales Order Simulation").exec(createOrder)

    init {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(10.0).during(60)
            )
        ).protocols(httpProtocol)
    }
}

data class SalesOrderRequest(
    @JsonProperty("num_item")
    val numItem: Int,
    @JsonProperty("product_id")
    val productId: String,
    val price: BigDecimal,
    val quantity: Int,
    @JsonProperty("created_by")
    val createdBy: String
)