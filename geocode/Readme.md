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

This service aims to fetch an address and coordinates based on input string
with an optional argument of type of desired return.

### Structured

In this service the search is structured in specific field, taking data in more specific way.
Could be used to single or multi query.

### Reverse

This service is focused in fetch address description data based in specific coordinates.
Could be used to single or multi query.

### Cross Cities

In this service we take as return the list of borders crossed by a route formed by a sequential list of coordinates.