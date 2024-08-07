# MapLink SDK - Geocode

Geocode API contains to fetch geographic data, like addresses and coordinates, they are segregated base on input type:

1. [Suggestions](#suggestions)
2. [Structured](#structured)
3. [Reverse](#reverse)
4. [Cross Cities](#cross-cities)

## Usage

Geocode API has two interfaces:

### Asynchronous

After SDK initialization, could be obtained with:

    GeocodeAsyncAPI.getInstance()

Returns a `CompletableFuture<SuggestionResult>` immediately not blocking caller thread, following default Java reactive
interface.

### Synchronous

After SDK initialization, could be obtained with:

    GeocodeSyncAPI.getInstance()

Returns a `SuggestionResult` directly and block called Thread while te request is running.

## Return type

All Geocode Services returns an object of type `SuggestionsResult` which is a Collection of `Suggestion` objects.

The `Suggestion` object contains:

1. `type` field who define type of this return (STATE, CITY, ZIPCODE, ROAD, etc..)
2. `score` field with a value in range 0 - 100 representing estimated result quality
3. `address` field composed by many fields with address details and the respective coordinates at `mainLocation` field.

## Services

### Suggestions

This service aims to fetch an address and coordinates based on input string.
Could be used in following ways:

    api.suggestions("sao paulo")

    api.suggestions("sao paulo", Type.CITY)

    api.suggestions(SuggestionsRequest.builder()
                        .query("sao paulo")
                        .build()
    )

### Structured

In this service the search is structured in specific field, taking data in more specific way.
Could be used to single or multi query. Could be used in following ways:

#### Single Structured queries

    api.structured(StructuredRequest.ofCity("reqId", "sao paulo", "sp")

    api.structured(StructuredRequest.of("reqId")
                        .city("sao paulo")
                        .build()
    )

#### Multi Structured queries

    api.structured(StructuredRequest.multi(
                    StructuredRequest.ofCity("reqId1", "sao paulo", "sp")
                    StructuredRequest.ofState("reqId2", "sp")
                    StructuredRequest.of("reqId3")
                        .city("sao paulo")
                        .build()
    )

### Reverse

This service is focused in fetch address description data based in specific coordinates.
Could be used to single or multi query as following:

#### Single Reverse queries

    api.reverse(ReverseRequest.entry(-23.6824124, -46.5952992))

#### Multi Reverse queries

    api.reverse(
        ReverseRequest.entry("reqId1", -23.6824124, -46.5952992)
        ReverseRequest.entry("reqId2", new BigDecimal("-23.6824124"), new BigDecimal("-46.5952992"))
        ReverseRequest.entry("reqId3", -23.6824124, -46.5952992, 500)
    )

### Cross Cities

In this service we take as return the list of borders crossed by a route formed by a sequential list of coordinates.
Could be used in following ways:

    api.crossCities(CrossCitiesRequest.builder()
                        .point(-23.6824124, -46.5952992)
                        .point(new BigDecimal("-24.6100581"), new BigDecimal("-52.4423937"))
                        .build()
    )

## Optional Parameters

### Type

type parameter can be specified to indicate the type of desired return.

    api.suggestions(SuggestionsRequest.builder()
                        .query("sao paulo")
                        .type(Type.CITY)
                        .build()
    )

### Last Mile

lastMile parameter can be included in suggestions,
as well as in single and multi structured queries, to focus the search on last mile results.

#### Example for Suggestions

    api.suggestions(SuggestionsRequest.builder()
                        .query("alameda campinas, 579, sao paulo - sp")
                        .lastMile(true)
                        .build()
    )

#### Example for Single Structured

    api.structured(StructuredRequest.of("reqId")
                        .city("sao paulo")
                        .road("alameda campinas")
                        .number("579")
                        .lastMile(true)
                        .build()
    )

#### Example for Multi Structured

    val multiRequest = StructuredRequest.multi(
                    StructuredRequest.ofCity("reqId1", "sao paulo", "sp"),
                    StructuredRequest.of("reqId2")
                        .city("sao paulo")
                        .road("alameda campinas")
                        .number("579")
                        .build()
    );
    multiRequest.setLastMile(true);

    api.structured(multiRequest)

## Extension List

* [Geocode Google Maps Extension](../geocode-extensions/google-maps-extension/Readme.md)