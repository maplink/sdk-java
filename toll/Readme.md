# MapLink SDK - Toll

Toll API - toll costs in route calculator

1. [Calculations](#calculations)

## Usage

Toll API has two interfaces:

### Asynchronous

After SDK initialization, could be obtained with:

    TollAsyncAPI.getInstance()

Returns a `CompletableFuture<TollCalculationResult>` immediately not blocking caller thread, following default Java reactive
interface.

### Synchronous

After SDK initialization, could be obtained with:

    TollSyncAPI.getInstance()

Returns a `TollCalculationResult` directly and block called Thread while te request is running.

## Services

### Calculations

Calculate tolls in route based on list of legs with coordinates

Returns an object of type `TollCalculationResult` which is a Collection of `LegResult` objects and `totalCosts` field.
