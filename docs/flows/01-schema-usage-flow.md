# Fluxo 01 — Uso dos schemas

1. Serviço consumidor adiciona dependência `<dominio>-schema` no `pom.xml`
2. Usa os POJOs para serializar/deserializar JSON via Jackson
3. A configuração Jackson vem de `json-mapper-jackson`
4. Schemas incluem Builders para criação imutável
