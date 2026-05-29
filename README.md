# sdk-java

Biblioteca Java core que contém todos os schemas e contratos (POJOs) usados pelos serviços do ecossistema Maplink (REST e Messaging). Também inclui clientes HTTP embutidos (Java 11 HttpClient) para uso interno.

## Contextos e fluxos

- Contexto funcional e arquitetural: [docs/contexts/sdk-java-context.md](docs/contexts/sdk-java-context.md)
- Visão de arquitetura: [docs/contexts/00-architecture-overview.md](docs/contexts/00-architecture-overview.md)
- Fluxo de uso dos schemas: [docs/flows/01-schema-usage-flow.md](docs/flows/01-schema-usage-flow.md)
- Fluxo de uso dos clientes HTTP: [docs/flows/02-http-client-usage-flow.md](docs/flows/02-http-client-usage-flow.md)

## Contexto para IA e revisão automatizada

- Contexto estruturado para Claude: [docs/ai-context.yaml](docs/ai-context.yaml)
- Skill de code review: [.claude/skills/claude-code-review/SKILL.md](.claude/skills/claude-code-review/SKILL.md)
- Skill de doc review: [.claude/skills/claude-doc-review/SKILL.md](.claude/skills/claude-doc-review/SKILL.md)

## O que a biblioteca faz

- Define schemas (POJOs Jackson) para todas as APIs: geocode, freight, toll, trip, planning, emission, place, tracking, restriction-zone
- Provê clientes HTTP (`geocode`, `freight`, `toll`, `trip`, `place`, `emission`) via Java 11 HttpClient
- Módulo `core` com primitivas comuns; `core-defaults` com configurações default

## Módulos Maven

| Módulo | Papel |
|---|---|
| `core` | Primitivas comuns |
| `core-defaults` | Configurações default |
| `geocode` / `geocode-schema` / `geocode-extensions` | Cliente + schemas de geocodificação |
| `freight` / `freight-schema` | Cliente + schemas de frete |
| `toll` / `toll-schema` | Cliente + schemas de pedágio |
| `trip` / `trip-schema` | Cliente + schemas de viagem |
| `place` / `place-schema` | Cliente + schemas de place |
| `emission` / `emission-schema` | Cliente + schemas de emissão |
| `restriction-zone-schema` | Schemas de zonas de restrição |
| `tracking-schema` | Schemas de rastreamento |
| `planning-schema` | Schemas de roteirização |
| `json-mapper-jackson` | Configuração Jackson |
| `http-engine-java11-client` | Motor HTTP Java 11 |

## Como compilar

```bash
mvn clean install
```
