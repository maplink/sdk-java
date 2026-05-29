# sdk-java — Visão de arquitetura

- Biblioteca Maven multi-módulo (sem runtime HTTP)
- Build: `mvn clean install` para todos os módulos
- Publicação no Nexus interno da Maplink
- Cada domínio tem um par `<dominio>` (cliente) + `<dominio>-schema` (POJOs)
- Serialização via `json-mapper-jackson` (Jackson centralizado)
- Motor HTTP via `http-engine-java11-client` (Java 11 HttpClient)
