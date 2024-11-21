# Gerenciamento de Pedidos

Um sistema desenvolvido em Java 17 com Spring Boot para gerenciamento de pedidos. A aplicação utiliza Flyway para versionamento de banco de dados, Testcontainers para testes integrados e Test MVC para validação de endpoints. Inclui também testes de carga com Gatling localizados na pasta `tests/performance`.

---

## Funcionalidades

- **Recebimento de pedidos via POST**: O sistema recebe os pedidos e os envia para uma fila **Amazon SQS**.
- **Processamento de pedidos via job**: Um job consome mensagens da fila SQS, processa os pedidos e os armazena no banco de dados.
- **Listagem de pedidos**: Endpoint para buscar todos os pedidos paginados.
- **Detalhamento de pedidos**: Endpoint para visualizar informações detalhadas de um pedido específico.

---

## Arquitetura Atual (AS-IS)

A arquitetura do sistema é representada pelo seguinte diagrama:

![](img/arch.png)

---

## Fluxograma

O fluxo de funcionamento do sistema pode ser visualizado no diagrama abaixo:

![](img/fluxo.png)

---

## Testes de Performance

Os testes de carga foram configurados utilizando **Gatling**, e os resultados podem ser visualizados no seguinte gráfico:

![](img/simulation.png)

Os testes estão localizados na pasta `tests/performance` e podem ser executados com o seguinte comando:

```shell
./gradlew gatlingRun
