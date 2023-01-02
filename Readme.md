# MapLink SDK

![Test Coverage](.github/badges/jacoco.svg)
![Test Coverage - Branches](.github/badges/branches.svg)
![Build Status](https://github.com/maplink/sdk-java/actions/workflows/maven.yml/badge.svg)

## Services List

* [Geocode API](./geocode/Readme.md)
* [Emission API](./emission/Readme.md)
* [Toll API](./toll/Readme.md)
* [Place API](./place/Readme.md)

## Usage

Usage is based on what API do you need,
all APIs mapped by this SDK are available at [services list](#services-list),
to understand the correct usage see our [Getting Started Guide](#getting-started)

### Getting started

First of all, is necessary to add SDK dependency to our project.
Here we will use `geocode` API, but this is valid to any API client in this SDK

To default SDK usage we provide a dependency, `sdk-core-defaults`,
with default implementation of Http Client based on Java 11
and Json Mapper base on Jackson, all of these can be customized too.

Maven:

    <dependency>
        <groupId>global.maplink</groupId>
        <artifactId>sdk-core-defaults</artifactId>
        <version>${sdk.version}</version>
    </dependency>

    <dependency>
        <groupId>global.maplink</groupId>
        <artifactId>sdk-geocode</artifactId>
        <version>${sdk.version}</version>
    </dependency>

Gradle (Groovy DSL):

    implementation 'global.maplink:sdk-core-defaults:$sdkVersion'
    implementation 'global.maplink:sdk-geocode:$sdkVersion'

Gradle (Kotlin DSL):

    implementation("global.maplink:sdk-core-defaults:$sdkVersion")
    implementation("global.maplink:sdk-geocode:$sdkVersion")

We'll use environment based authentication, this is one of the [authentication methods](#authentication) available.
In this authentication method we need to provide as environment variable our credentials, 
client id must be available on `MAPLINK_CLIENT_ID` variable, and secret key in `MAPLINK_SECRET` variable.

And then use in your project:

    // 1. Initialize SDK Globally, must be done only once in app lifetime
    MapLinkSDK.configure().initialize();

    // 2. Take instance of GeocodeAPI with Async interface
    GeocodeAsyncAPI api = GeocodeAsyncAPI.getInstance();

    // 3. Do a request to suggestions path and print results
    api.suggestions("sao paulo").thenAccept(System.out::println);

API usage is really simple, at first we need to [initialize MapLinkSDK configuration](#sdk-initialization) (Step 1),
this need to be done only once in application lifetime, here we can use defaults,
or customize many aspects of SDK behavior, following convention over configuration pattern.

After initialize SDK we can take any API instance, both Sync or Async interfaces,
to achieve it we need to call `.getInstance()` method in desired service.

With API instances we can call any of the services provided by this API,
for Sync interfaces the result is returns directly,
for Async results it returns a `CompletatableFuture`

### SDK Initialization

SDK initialization needs to be done only once in application lifetime:

    MapLinkSDK.configure()
        // all optionals configurations can be defined here
        .initialize()

Current instance of configured SDK could be accessed in any point of application with:

    MapLinkSDK.getInstance()

There is an option to reset current authentication using:
    
    MapLinkSDK.resetConfiguration()

#### Authentication

Our SDK access MapLink Platform APIs who need valid credentials pair,
used by fetch OAuth2 API token, which could be provided in following ways:

**Environment Variables Credentials** use credentials from 
`MAPLINK_CLIENT_ID` and `MAPLINK_SECRET` environment variables or
`maplink.clientId` and `maplink.secret` properties.
It is default, but could be defined explicitly at SDK initialization:

    MapLinkSDK.configure()
        .with(EnvMapLinkCredentials())
    
**Provided Credentials** is another way to define credentials, it's programmatically defined at SDK initialization:

    MapLinkSDK.configure()
        .with(ProvidedMapLinkCredentials("clientID", "secret"))

Using configured credentials SDK engine will manage OAuth2 token requests, lifetime and renewal automatically.

#### Environment

Environment is a configurations used to define which API will be called by SDK requests.
SDK uses as default `PRODUCTION` environment, but for testing propose there is a `HOMOLOG` env too.

They are available at `EnvironmentCatalog` enum, and could be defined in SDK initialization with:

    MapLinkSDK.configure()
        .with(EnvironmentCatalog.HOMOLOG)

