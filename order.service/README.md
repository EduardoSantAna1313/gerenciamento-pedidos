## Order.service

## Setup

1. Subir a infra (db e sqs) via docker compose.

    ```shell
    docker compose -f docker-compose.yaml up
    ```

2. Executar a classe `OrderApplication`.

## Tecnologias

- Java17
- Spring
- Flyway
- Docker
- Testcontainers
- Spotless (formatador)

### Docker

Buildar a imagem:

```sh
mvn clean package
docker build -t order:v1 .
```

### Formatar codigo

```sh
mvn spotless:apply
```