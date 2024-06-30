# MapLink SDK

![Test Coverage](.github/badges/jacoco.svg)
![Test Coverage - Branches](.github/badges/branches.svg)
![Build Status](https://github.com/maplink/sdk-java/actions/workflows/maven.yml/badge.svg)

## Services List

* [Geocode API](./geocode/Readme.md)
* [Emission API](./emission/Readme.md)
* [Toll API](./toll/Readme.md)
* [Place API](./place/Readme.md)
* [Freight API](./freight/Readme.md)

## Usage

Usage is based on what API you need,
all APIs mapped by this SDK are available at [services list](#services-list),
to understand the correct usage, see our [Getting Started Guide](#getting-started).

### Getting started

First of all, it is necessary to add SDK dependency to our project.
Here we will use `geocode` API, which is valid to any API client in this SDK.

With the default SDK usage, we provide a dependency, `sdk-core-defaults`,
with default implementation of Http Client based on Java 11
and Json Mapper based on Jackson, all of which can be customized, too.

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

We'll use environment based authentication, which is one of the [authentication methods](#authentication) available.
In this authentication method, we need to provide our credentials as an environment variable, 
the client id must be available in the `MAPLINK_CLIENT_ID` variable, and the secret key in the `MAPLINK_SECRET` variable.

And then use in your project:

    // 1. Initialize SDK Globally, must be done only once in app's lifetime
    MapLinkSDK.configure().initialize();

    // 2. Take instance of GeocodeAPI with Async interface
    GeocodeAsyncAPI api = GeocodeAsyncAPI.getInstance();

    // 3. Make a request to suggestions path and print results
    api.suggestions("sao paulo").thenAccept(System.out::println);

API usage is really simple. First, we need to [initialize MapLinkSDK configuration](#sdk-initialization) (Step 1),
this needs to be done only once in the application lifetime, here we can use defaults,
or customize many aspects of SDK behavior by following conventions over configuration patterns.

After initializing the SDK, we can take any API instance, both Sync or Async interfaces,
To achieve this, we need to call the `.getInstance()` method in the desired service.

With API instances, we can call any of the services provided by this API,
for Sync interfaces, the result is returned directly,
for Async results, it returns a `CompletatableFuture`.

### SDK Initialization

SDK initialization needs to be done only once in the application lifetime:

    MapLinkSDK.configure()
        // all optionals configurations can be defined here
        .initialize()

The current instance of configured SDK could be accessed at any point of application with:

    MapLinkSDK.getInstance()

There is an option to reset the current authentication using:
    
    MapLinkSDK.resetConfiguration()

#### Authentication

Our SDK access MapLink Platform APIs that need valid credentials pair,
used by fetching the OAuth2 API token, which is attained in the following ways:

**Environment Variables Credentials** use credentials from 
`MAPLINK_CLIENT_ID` and `MAPLINK_SECRET` environment variables or
`maplink.clientId` and `maplink.secret` properties.
It is default, but could be defined explicitly at SDK initialization:

    MapLinkSDK.configure()
        .with(EnvMapLinkCredentials())
    
**Provided Credentials** is another way to define credentials, it's programmatically defined at SDK initialization:

    MapLinkSDK.configure()
        .with(ProvidedMapLinkCredentials("clientID", "secret"))

Using configured credentials the SDK engine will manage OAuth2 token requests, lifetime and renewal automatically.

#### Environment

Environment is a configuration used to define which API will be called by SDK requests.
By default the SDK uses the `PRODUCTION` environment, but for testing purposes there is a `HOMOLOG` env too.

They are available at `EnvironmentCatalog` enum, and could be defined in SDK initialization with:

    MapLinkSDK.configure()
        .with(EnvironmentCatalog.HOMOLOG)

