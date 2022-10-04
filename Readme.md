# MapLink SDK

![Test Coverage](.github/badges/jacoco.svg)
![Test Coverage - Branches](.github/badges/branches.svg)
![Build Status](https://github.com/maplink/sdk-java/actions/workflows/maven.yml/badge.svg)

## Services List

* [Geocode API](./geocode/Readme.md)

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

And then use in your project:

    // 1. Initialize SDK Globally, must be done only once in app lifetime
    MapLinkSDK.configure().initialize();

    // 2. Take instance of GeocodeAPI with Async interface
    GeocodeAsyncAPI api = GeocodeAsyncAPI.getInstance();

    // 3. Do a request to suggestions path and print results
    api.suggestions("sao paulo").thenAccept(System.out::println);

API usage is really simple, at first we need to configure MapLinkSDK (Step 1),
this need to be done only once in application lifetime, here we can use defaults,
or customize many aspects of SDK behavior, following convention over configuration pattern.

After initialize SDK we can take any API instance, both Sync or Async interfaces,
to achieve it we need to call `.getInstance()` method in desired service.

With API instances we can call any of the services provided by this API,
for Sync interfaces the result is returns directly,
for Async results it returns a `CompletatableFuture`

