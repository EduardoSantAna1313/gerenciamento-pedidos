## Testes de performance

```shell
mvn galting:test
```

O comando acima irá executar a simulation `src/test/kotlin/simulations/OrderSimulation.kt`.

A simulação é configurada no seguinte trecho:

```kotlin
val steps = listOf(
    constantUsersPerSec(4.0).during(5),
    constantUsersPerSec(4.0).during(10).randomized(),
    constantUsersPerSec(45.0).during(400).randomized(),
)
```

É executado dois steps iniciais para aquecer a aplicação e por ultimo realiza o teste de performance durante 400 segundos.

## Tecnologias

- Java17
- Kotlin
- Gatling