package simulations

import io.gatling.javaapi.core.ChainBuilder
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.*
import io.gatling.javaapi.http.HttpProtocolBuilder
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class OrderSimulation: Simulation() {

    private val httpProtocol: HttpProtocolBuilder = http.baseUrl("http://localhost:9999/v1")
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")

    private val execs: ChainBuilder =
        exec(
            http("POST")
                .post ("/orders")
                .body(StringBody { createBody() } )
                .check(
                    status().`is`(201)
                )
        )
        // Print the response body
        .exec{ session ->
            //    println(session.get("responseBody"))
            session
        }


    private fun createBody(): String {
        val rand = Random()

        val quantity = rand.nextInt(1, 10)

        val price = BigDecimal.valueOf(rand.nextDouble(0.0, 10000.0)).setScale(2, RoundingMode.HALF_UP)
        val body = """
            {
            	"created_by": "Eduardo",
            	"origin": "Performance Tests",
            	"items": [
            		{
            			"prodcut": "umidificador de ar",
            			"price": $price,
            			"quantity": $quantity
            		}
            	]
            }
        """.trimIndent()

        //println(body)
        return body;
    }


    init {

        val steps = listOf(
            constantUsersPerSec(4.0).during(5),
            constantUsersPerSec(4.0).during(10).randomized(),
            constantUsersPerSec(45.0).during(400).randomized(),
        )

        val scenario: ScenarioBuilder = scenario("Orders Scenario").exec(execs)

        setUp(
            scenario.injectOpen(steps)
        )
            .protocols(httpProtocol)
    }

}