# MapLink SDK - Freight

Freight API - minimum value of a given freight calculation API

1. [Calculations](#calculations)

## Usage

Freight API has two interfaces:

### Asynchronous

After SDK initialization, could be obtained with:

    FreightAsyncAPI.getInstance()

Returns a `CompletableFuture<FreightCalculationResponse>` immediately not blocking caller thread, following default Java reactive
interface.

### Synchronous

After SDK initialization, could be obtained with:

    FreightSyncAPI.getInstance()

Returns a `FreightCalculationResponse` directly and block called Thread while te request is running.

## Services

### Calculations

Calculate minimum value of a given freight

Returns an object of type `FreightCalculationResponse` containing `minimumFreight` field with calculated result.
