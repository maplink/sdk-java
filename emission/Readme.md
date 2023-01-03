# MapLink SDK - Emission

Emission API - CO2 emission calculator

1. [Calculations](#calculations)

## Usage

Emission API has two interfaces:

### Asynchronous

After SDK initialization, could be obtained with:

    EmissionAsyncAPI.getInstance()

Returns a `CompletableFuture<EmissionResponse>` immediately not blocking caller thread, following default Java reactive
interface.

### Synchronous

After SDK initialization, could be obtained with:

    EmissionSyncAPI.getInstance()

Returns a `EmissionResponse` directly and block called Thread while te request is running.

## Services

### Calculations

Calculate CO2 emission based in fuel consumed and distance

Returns an object of type `EmissionResponse` containing `totalEmission` field with calculated result.
