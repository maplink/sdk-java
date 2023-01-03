# MapLink SDK - Place

Place API - Places in route

1. [Calculations](#calculations)

## Usage

Place API has two interfaces:

### Asynchronous

After SDK initialization, could be obtained with:

    PlaceAsyncAPI.getInstance()

Returns a `CompletableFuture<PlaceRouteResponse>` immediately not blocking caller thread, following default Java reactive
interface.

### Synchronous

After SDK initialization, could be obtained with:

    PlaceAsyncAPI.getInstance()

Returns a `PlaceRouteResponse` directly and block called Thread while te request is running.

## Services

### Calculations

Fetch places who match with given route

Returns an object of type `PlaceRouteResponse` containing a list of `LegResult`
