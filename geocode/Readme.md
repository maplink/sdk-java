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

This service aims to fetch an address and coordinates based on an input string, with optional arguments for the type of desired return and last mile results.
Could be used in following ways:

    api.suggestions("sao paulo")

    api.suggestions("sao paulo", Type.CITY)

    api.suggestions(SuggestionsRequest.builder()
                        .query("sao paulo")
                        .type(Type.CITY)
                        .build()
    )

The search will focus on last mile results only if the .lastMile parameter is passed.

### Structured

In this service the search is structured in specific field, taking data in more specific way.
Could be used to single or multi query. Could be used in following ways:

#### Single Structured queries

    api.structured(StructuredRequest.ofCity("reqId", "sao paulo", "sp")

    api.structured(StructuredRequest.of("reqId")
                        .city("sao paulo")
                        .lastMile(true)
                        .build()
    )

#### Multi Structured queries

    val multiRequest = StructuredRequest.multi(
                    StructuredRequest.ofCity("reqId1", "sao paulo", "sp"),
                    StructuredRequest.ofState("reqId2", "sp"),
                    StructuredRequest.of("reqId3")
                        .city("sao paulo")
                        .build()
    );
    multiRequest.setLastMile(true);

    api.structured(multiRequest)

If you want to focus on last mile results, just pass lastMile as true in either single or multi structured queries.

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

## Extension List

* [Geocode Google Maps Extension](../geocode-extensions/google-maps-extension/Readme.md)
