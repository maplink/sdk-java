# sdk-java — Contexto funcional e arquitetural

`sdk-java` é uma biblioteca Maven multi-módulo que define todos os schemas (POJOs Jackson) e clientes HTTP usados pelos serviços da plataforma Maplink.

## Não é um serviço HTTP

Não possui runtime próprio. É importado como dependência Maven pelos serviços.

## Módulos de schema

- `geocode-schema`, `freight-schema`, `toll-schema`, `trip-schema`, `place-schema`, `emission-schema`
- `restriction-zone-schema`, `tracking-schema`, `planning-schema`

## Módulos de cliente

- `geocode`, `freight`, `toll`, `trip`, `place`, `emission` — clientes via `http-engine-java11-client`

## Módulos base

- `core` — primitivas comuns
- `core-defaults` — configurações default
- `json-mapper-jackson` — configuração Jackson centralizada
