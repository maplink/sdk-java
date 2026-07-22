---
name: claude-code-review
description: Revisa mudanças do sdk-java (Java 8 nos módulos de schema/cliente, Java 11 no http-engine e planning-schema / Maven multi-módulo / SDK Java cliente das APIs Maplink, publicado como jar no Maven Central e no Nexus interno, sem runtime HTTP próprio / Java 11 HttpClient assíncrono / Jackson + Lombok / OAuth2 client_credentials) com foco em risco real de bug, breaking change de API pública consumida por clientes externos e serviços Maplink, quebra de (de)serialização Jackson, regressão de autenticação/roteamento e ausência de teste relevante.
argument-hint: [contexto opcional]
---

# Claude Code Review — sdk-java

Use este skill para revisar mudanças do `sdk-java` com foco em **risco real e verificável**.

O `sdk-java` **não é um serviço HTTP** — é o **SDK Java cliente** das APIs da plataforma Maplink,
distribuído como **biblioteca Maven multi-módulo** (packaging `pom`, artefato raiz `global.maplink:sdk`
`1.5.38-SNAPSHOT`) e publicado como jar no **Maven Central** (Sonatype OSSRH, licença MIT, repositório
público) e no **Nexus interno LBS**. Ele é consumido tanto por **aplicações externas** quanto por
**outros serviços Maplink**. O ponto de entrada é `MapLinkSDK.configure()...initialize()` (singleton
estático), e cada domínio expõe um cliente `{Dominio}SyncAPI`/`{Dominio}AsyncAPI` obtido via
`getInstance()`; o async devolve `CompletableFuture<T>`. Sob o capô o SDK autentica via **OAuth2
client_credentials**, faz a chamada HTTP através do `http-engine-java11-client` (Java 11 `HttpClient`) e
(de)serializa os POJOs de schema via Jackson centralizado no `json-mapper-jackson`. Como é jar
compartilhado e amplamente consumido, o "domínio" é **a própria API pública do SDK** (classes, métodos,
builders e POJOs de schema expostos) mais os **contratos JSON das APIs remotas** que ele consome:
qualquer mudança de assinatura, campo, tipo, default ou de (de)serialização pode quebrar **binariamente
ou por comportamento** os consumidores no build ou em runtime.

## Stack e tecnologias (referência para entender impacto — NÃO é gatilho de finding)

| Tecnologia | Versão | O que significa para o review |
|---|---|---|
| Java (bytecode alvo) | **8** (`maven.compiler.source/target=8` no pom raiz) | Módulos de schema/cliente publicam bytecode Java 8 — **não** introduza APIs de Java 9+ (`List.of`, `String.isBlank`, `var`, `Optional.orElseThrow()` sem arg) nesses módulos: quebra consumidores em Java 8 |
| Java (exceções de alvo) | **11** em `http-engine-java11-client` e `planning-schema` | Esses dois módulos compilam para Java 11 por design (o engine usa `java.net.http.HttpClient`) — APIs de Java 11 aqui são intencionais |
| Maven (multi-módulo) | `packaging=pom`, 20 módulos | Cada domínio tem par `<dominio>` (cliente) + `<dominio>-schema` (POJOs); jars publicados individualmente |
| CI | JDK 11 (temurin), `mvn -B install` | Build compila com JDK 11 mas com alvo 8; badge JaCoCo gerado a partir de `jacoco-report` |
| Lombok | 1.18.36 | `@Data`/`@Builder`/`@RequiredArgsConstructor(staticName="of")`/`@NoArgsConstructor(force=true, access=PRIVATE)`/`@Getter`/`@Slf4j`/`val` — ver guardrails. Build usa delombok + javadoc + source jars |
| Jackson (+ jsr310) | 2.18.2 | (De)serialização de **todos** os schemas. Config central em `JacksonJsonMapperImpl` — ver guardrails de serialização |
| SLF4J + Logback | 2.0.16 / 1.5.16 | Logging via `@Slf4j` (Lombok `log`) — nunca injetar logger |
| geohash (`ch.hsr`) | 1.4.0 | Utilitário geoespacial pontual — não reporte como "dependência morta" |
| JUnit 5 + Mockito + AssertJ | 5.11.4 / 5.15.2 / 3.27.3 | Testes **unitários**; clientes HTTP testados contra **WireMock**, não a API real |
| WireMock | 3.10.0 | Stub das respostas HTTP das APIs Maplink nos testes de cliente |
| json-unit-assertj | 2.38.0 | Asserções sobre o JSON serializado (contrato de payload) |
| JaCoCo | 0.8.12 | Cobertura agregada no módulo `jacoco-report` (meta de código novo: ~80%) |

Mapa dos módulos (o que cada um expõe — a superfície pública é o contrato):

| Módulo (`artifactId`) | Papel | Superfície-chave |
|---|---|---|
| `core` (`sdk-core`) | Núcleo do SDK | `MapLinkSDK`/`MapLinkSDK.Configurator`, `MapLinkServiceRequest<T>`, `MapLinkCredentials`, `TokenProvider`/`OAuthTokenProvider`/`CachedTokenProviderDecorator`, `Environment`/`EnvironmentCatalog`, `HttpAsyncEngine`, `Request`/`Response`, `SdkExtension` |
| `core-defaults` (`sdk-core-defaults`) | Wiring dos defaults (engine Java 11 + Jackson) | Só testes de fiação (`HttpAsyncEngineTest`, `TokenProviderTest`, `JsonMapperTest`) |
| `json-mapper-jackson` | Serialização centralizada | `JacksonJsonMapperImpl`, `MaplinkSdkModule` (+ codecs `MaplinkPoint`/`MaplinkPoints`) |
| `http-engine-java11-client` | Motor HTTP assíncrono | `HttpAsyncEngineJava11Impl` (Java 11 `HttpClient`, `CompletableFuture`) |
| `geocode` + `geocode-schema` (+ `geocode-extensions`) | Cliente + schemas de geocodificação (`GeocodeSync/AsyncAPI`, `GeocodeVersion.V1/V2`, `SuggestionsRequest`, `ReverseRequest`...) |
| `freight` + `freight-schema` | Cliente + schemas de frete (`FreightSync/AsyncAPI`, `FreightCalculationRequest` PATH `freight/v1/calculations`) |
| `toll` + `toll-schema` | Cliente + schemas de pedágio (`TollSync/AsyncAPI`, `TollCalculationRequest`) |
| `trip` + `trip-schema` | Cliente + schemas de viagem (`TripSync/AsyncAPI`, `v1` problem / `v2` solution) |
| `place` + `place-schema` | Cliente + schemas de place (`PlaceSync/AsyncAPI`, CRUD + rota) |
| `emission` + `emission-schema` | Cliente + schemas de emissão (`EmissionSync/AsyncAPI`) |
| `restriction-zone-schema` / `tracking-schema` / `planning-schema` | Schemas-only (sem cliente HTTP dedicado) |
| `jacoco-report` | Agrega cobertura de todos os módulos | — |

## Regra de ouro — o diff é o sujeito, o contexto é só referência

O objeto do review é **o que mudou no diff**. Todo o restante (esta skill, `docs/ai-context.yaml`,
`docs/**`, `.kiro/steering/**` se existir, `README.md`/`Readme.md`) existe apenas para você **entender o
impacto** de uma mudança — **não é fonte de findings**.

- **Nunca** reporte algo só porque uma área foi citada como "sensível" ou aparece numa lista de atenção
  (ex.: `doc_sensitive_paths` ou `ai_review.focus` do `ai-context.yaml`, que lista "compatibilidade
  retroativa" e "Builder patterns"). Listas orientam *onde olhar*, não *o que reportar*.
- Um finding exige uma **linha concreta alterada no diff** com um defeito demonstrável. Sem linha
  alterada com defeito, não há finding.
- Documentos escritos pelo autor **neste mesmo PR** (ex.: `docs/flows/**`, `docs/contexts/**`,
  `docs/ai-context.yaml`, `README.md`) descrevem **intenção**, não contrato externo. Não os audite como
  especificação a cumprir, e não gere finding a partir de ressalvas que o próprio autor escreveu.
- Planning docs kiro (`.kiro/specs/**`, `.kiro/steering/**`) descrevem **intenção/estado desejado**, não
  o estado do código. Não os trate como fonte de finding.

## Gate por achado — verifique ou descarte

Antes de incluir qualquer finding, você precisa conseguir responder, com base no código fornecido:

1. **Qual a linha alterada** (path + linha) que contém o defeito?
2. **Qual input ou estado concreto** dispara a falha? (ex.: método público de `{Dominio}Sync/AsyncAPI`
   removido/renomeado que um consumidor chama; campo obrigatório retirado de um schema `*Request`/
   `*Response`; `@JsonProperty`/nome de campo alterado num POJO já (de)serializado; `PATH` de um request
   apontando para rota errada; nome de parâmetro/chave OAuth mudado em `OAuthTokenProvider`; API de Java
   9+ usada num módulo com alvo Java 8)
3. **Qual o resultado observável** da falha? (falha de compilação/binária no consumidor;
   `NoSuchMethodError`/`NoClassDefFoundError` em runtime; JSON que deixa de (de)serializar ou serializa
   com campo perdido/renomeado; request roteado para host/rota errada; **todas** as chamadas rejeitadas
   por auth quando o token deixa de ser obtido/renovado; `CompletableFuture` completando
   excepcionalmente)

Se você **não conseguir construir esse caminho de falha concreto**, **não reporte como finding**. No
máximo registre como `residual_risk` (ponto de atenção não bloqueante), e somente se for realmente
acionável.

Não reporte suspeitas hedge ("pode ser que", "verificar se", "talvez não esteja correto"). Se a sua
própria descrição do problema já contém a confirmação de que está correto, **isso não é um finding** —
descarte.

## Auto-verificação obrigatória (execute ANTES de emitir cada finding)

Para **cada** finding candidato, responda internamente, nesta ordem:

1. **Cite a linha alterada** — copie o texto exato da linha com `+` no diff onde está o defeito. Se o
   defeito não está numa linha `+` (é comportamento pré-existente não tocado), **descarte**.
2. **Escreva o caminho de falha** — input/estado concreto → resultado observável. Se você não
   consegue escrever isso sem usar "pode", "talvez", "verificar se", **descarte**.
3. **Tente refutar** — o `.map`/`when`/guard/condição citado já trata o caso? Os testes/CI existentes
   já exercitam esse caminho e passam? Se o finding só sobrevive porque você *não* olhou o entorno,
   **descarte**.

Regra de desempate: **na dúvida, descarte**. Um finding a menos não custa nada; um falso positivo
num gate bloqueante custa confiança.

## Rubrica de severidade (calibrada a SDK cliente / biblioteca de contrato)

- `high` — **só** com caminho de falha demonstrável de impacto alto:
  - **Breaking change de API pública consumida**: remover/renomear/mudar assinatura, tipo de retorno ou
    tornar obrigatório um parâmetro de método/construtor `public` da superfície do SDK — `MapLinkSDK`/
    `Configurator` (`configure`, `initialize`, `getInstance`, `with(...)`), os `getInstance()` de
    `{Dominio}Sync/AsyncAPI`, os métodos de negócio dos clientes, ou builders/campos de schema já
    expostos. Quebra o build (fonte) **ou** o runtime (`NoSuchMethodError`) dos consumidores.
  - **Quebra de (de)serialização Jackson do contrato**: renomear/remover campo de um POJO de schema já
    trafegado, alterar `@JsonProperty`/`@JsonInclude`/`@JsonIgnore`/`access`, ou mudar a config central
    em `JacksonJsonMapperImpl`/`MaplinkSdkModule` (inclusão `NON_ABSENT`, datas como timestamp, codecs
    de `MaplinkPoint`) — afeta **todos** os domínios e pode fazer JSON válido deixar de parsear ou
    perder campo na resposta.
  - **Regressão de autenticação**: mudar nome de parâmetro/chave OAuth em `OAuthTokenProvider`
    (`client_id`/`client_secret`/`grant_type`/`access_token`/`expires_in`/`issued_at`), a rota
    `oauth/client_credential/accesstoken`, a leitura de credenciais (`MAPLINK_CLIENT_ID`/
    `MAPLINK_SECRET` ou `maplink.clientId`/`maplink.secret`), ou a lógica de expiração/cache do
    `CachedTokenProviderDecorator` — derruba **todas** as chamadas autenticadas.
  - **Roteamento errado**: alterar um `PATH` de request, o `getHost()` de `EnvironmentCatalog`
    (`https://api.maplink.global` / `https://pre-api.maplink.global`) ou `Environment.withService` de
    forma que uma chamada existente passe a resolver para host/rota errada.
  - **Incompatibilidade de versão de bytecode**: introduzir API de Java 9+ num módulo com alvo Java 8
    (todos menos `http-engine-java11-client` e `planning-schema`) — quebra consumidores/CI em Java 8.
  - NPE/crash em fluxo real de request introduzido pelo diff.
- `medium` — risco real de regressão ou de operabilidade com caminho de falha plausível
  (ex.: `validate()` de um `*Request` deixando passar payload inválido que a API remota rejeitaria;
  `split()`/junção de `SuggestionsResult` perdendo/duplicando resultado; parsing de resposta que passe
  a lançar em corpo válido; nova versão de API (`GeocodeVersion`) sem cliente correspondente).
- `low` — boa prática diretamente ligada a risco verificável (ex.: ausência de teste para novo endpoint
  de cliente, novo campo de schema (de)serializado, ou mudança no fluxo de token/roteamento).
- **"Adicione um `log.warn`", "considere documentar", "considere telemetria", "considere extrair
  para um método"** → no máximo `low`, e em geral pertencem a `residual_risks`. **Nunca `high`.**

Se você tem 5 findings e só 1 tem caminho de falha demonstrável, reporte 1. Menos findings de alta
confiança valem mais do que uma lista que o autor vai refutar.

## Guardrails de Java / Lombok / Jackson / SDK-jar (evite estes falsos positivos)

Estes são erros comuns de leitura ou padrões estabelecidos do projeto. **Não** os reporte:

- **Compatibilidade é assimétrica**: **adicionar** método, sobrecarga, campo opcional (com default) ou
  constante de enum **é compatível**; **remover/renomear/reordenar constante de enum, mudar assinatura
  pública, tipo, ou tornar um parâmetro/campo obrigatório é breaking**. Não trate toda mudança de
  schema/API como quebra — só a que altera/retira o que já é consumido. (Isto **substitui** a leitura
  ingênua do `ai-context.yaml` de que "todo campo novo precisa ser Optional".)
- **Jackson ignora campos desconhecidos** (`FAIL_ON_UNKNOWN_PROPERTIES` está **desabilitado** em
  `JacksonJsonMapperImpl`): um campo novo aparecendo numa **resposta remota** não quebra a
  desserialização, e **adicionar** um campo a um POJO de schema é retrocompatível. Não reporte "campo
  novo pode quebrar o parser".
- **Lombok é o padrão do projeto** — tratá-lo como bug é falso positivo:
  - `@Data`/`@Builder`/`@Getter` geram getters/setters/`equals`/`hashCode`/`toString`/builder — **não**
    reporte "falta getter/construtor/equals" nem "falta builder".
  - `@RequiredArgsConstructor(staticName = "of")` + `@NoArgsConstructor(force = true, access = PRIVATE)`
    coexistem de propósito (ex.: `FreightCalculationRequest`): o `of`/builder cria instância imutável e
    o construtor privado sem-args habilita a desserialização Jackson. Não sugira "remover construtor
    duplicado" nem "campos `final` impedem desserialização".
  - `@Slf4j` fornece `log` — nunca é injetado; `val` é inferência Lombok (tipo `final`), não Kotlin.
- **Anotações Jackson intencionais**: `@JsonProperty(access = WRITE_ONLY)`, `@JsonInclude(NON_EMPTY)`,
  `@JsonIgnore` (ex.: `planning-schema/.../solution/Solution.java`) moldam o contrato de (de)serialização
  de propósito. Não reporte como bug; só é finding se o **diff** alterá-las de forma que quebre JSON já
  trafegado.
- **Membros `@Deprecated` são mantidos por retrocompatibilidade** (ex.: `SuggestionsRequest.lastMile` /
  `PARAM_LAST_MILE`, marcado para remoção só na v2). **Não** sugira removê-los agora — removê-los seria a
  quebra, não mantê-los.
- **Java 11 é intencional em `http-engine-java11-client` e `planning-schema`** — usar `java.net.http`,
  `var` ou APIs de Java 11 **nesses** módulos não é erro. O erro seria o inverso: API de Java 9+ nos
  módulos com alvo Java 8.
- **Plain Java, sem framework/DI**: não há Quarkus/Spring/CDI. Instâncias vêm de `getInstance()`
  estático, builders e do singleton `MapLinkSDK`. Não sugira "transformar em bean", `@Inject`,
  `@Component` ou `@ApplicationScoped`.
- **API assíncrona devolve `CompletableFuture`**: exceções chegam encapsuladas em `CompletionException`
  ao chamar `join()`/`get()` — isso é o contrato de `CompletableFuture`, não um bug de tratamento de erro.
- **Testes são unitários** (JUnit 5 + Mockito + AssertJ): clientes HTTP são testados contra **WireMock**
  (respostas stubadas) e `TokenProvider`/`JsonMapper`/`HttpAsyncEngine` são mockados; asserções de
  payload usam `json-unit-assertj`. Sugerir "chamar a API Maplink real no teste" é anti-pattern deste
  repo. As credenciais `MAPLINK_CLIENT_ID`/`MAPLINK_SECRET` no CI servem só ao build/integração.

## Onde olhar (orientação, não checklist de findings)

Áreas onde uma mudança tende a ter impacto cross-consumer — investigue **se o diff as tocar**, mas só
reporte com caminho de falha concreto:

- **Superfície pública do SDK (contrato binário/fonte)**: `MapLinkSDK`/`Configurator`
  (`configure`/`initialize`/`getInstance`/`with(...)`), os `getInstance()` e métodos de negócio de cada
  `{Dominio}Sync/AsyncAPI`, `MapLinkServiceRequest<T>` (`asHttpRequest`/`getResponseParser`/`validate`),
  e os builders/campos dos POJOs `*Request`/`*Response`. Mudança que remova/renomeie/altere assinatura
  quebra o build ou o runtime dos consumidores e exige bump de versão.
- **(De)serialização Jackson**: config central em `JacksonJsonMapperImpl` (`NON_ABSENT`,
  `FAIL_ON_UNKNOWN_PROPERTIES` desabilitado, datas como timestamp) e `MaplinkSdkModule` (codecs de
  `MaplinkPoint`/`MaplinkPoints`); `@JsonProperty`/`@JsonInclude`/`@JsonIgnore` nos schemas; nomes de
  campo dos POJOs que casam com o JSON da API remota.
- **Autenticação**: `OAuthTokenProvider` (params/chaves OAuth, rota do token), `CachedTokenProviderDecorator`
  (expiração/renovação de `MapLinkToken`), `EnvMapLinkCredentials`/`ProvidedMapLinkCredentials`,
  `InvalidCredentialsException`. É a superfície de maior blast radius do repo.
- **Roteamento/ambiente**: constantes `PATH` dos requests, `EnvironmentCatalog` (hosts PRODUCTION/HOMOLOG),
  `Environment.withService`, e o `GeocodeVersion`/decorators que compõem a URL.
- **Motor HTTP**: `HttpAsyncEngineJava11Impl` e o contrato `Request`/`Response`/`RequestBody`
  (`Json`/`Form`) — mudança aqui afeta todos os clientes.
- **Compatibilidade de versão**: alvo Java 8 vs 11 por módulo; ciclo de vida de `@Deprecated`.
- **Ausência de teste** para endpoint/serialização/token/roteamento novo ou alterado (cobertura de
  código novo ~80%).

## O que NÃO avaliar

- estilo irrelevante
- comentário genérico sem impacto funcional
- refatoração fora do escopo da mudança
- achados já cobertos por Sonar ou Trivy
- o padrão estabelecido do projeto (Lombok, plain Java sem DI, WireMock nos testes — ver guardrails)
- dependências presentes mas sem uso ativo (ex.: `geohash`)
- comportamentos pré-existentes não tocados pelo diff
- membros `@Deprecated` mantidos de propósito para retrocompatibilidade
- planning docs kiro (`.kiro/specs/**`, `.kiro/steering/**`) — descrevem **intenção**, não o estado do código

## Procedimento

1. Leia o **diff** primeiro — ele define o que está sob review.
2. Use `docs/ai-context.yaml`, `docs/**`, `.kiro/steering/**` (se existir) e `README.md` apenas para
   entender consumidores, contrato e impacto — nunca como fonte de finding.
3. Para cada candidato a finding, aplique o **gate verify-or-drop** e a **auto-verificação** acima.
   Descarte o que não passar.
4. Atribua severidade pela rubrica. Rebaixe sugestões de log/doc/telemetria.
5. Se não restar nenhum finding com caminho de falha concreto, **diga explicitamente que não encontrou
   problemas relevantes**. Esse é um resultado válido e esperado.

> Este review **ajuda o reviewer humano**; não é gate bloqueante. Falso positivo custa confiança —
> prefira reportar de menos a reportar de mais.
