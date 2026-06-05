# Fluxo 02 — Uso dos clientes HTTP

1. Serviço consumidor adiciona dependência `<dominio>` no `pom.xml`
2. Instancia o cliente passando a URL base e credenciais
3. Motor HTTP é o `http-engine-java11-client` (Java 11 HttpClient)
4. Cliente faz chamada tipada à API Maplink e retorna POJO do schema correspondente
