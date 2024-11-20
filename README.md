# gerenciamento-pedidos

## Rodando local

1. Subindo infra, fila sqs e db.

```shell
docker compose up
```

2. Subindo apps:

```shell
docker compose -f docker-compose-lb.yaml up
```
