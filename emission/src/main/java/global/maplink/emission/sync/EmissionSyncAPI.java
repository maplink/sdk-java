package global.maplink.emission.sync;

import global.maplink.emission.async.EmissionAsyncAPI;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.emission.schema.EmissionResponse;
import global.maplink.emission.schema.Fuel;
import global.maplink.emission.schema.Source;
import global.maplink.env.Environment;

import java.math.BigDecimal;

public interface EmissionSyncAPI {

    default EmissionResponse calculate(
            Source source,
            Fuel fuelType,
            BigDecimal averageComsumption,
            BigDecimal fuelPrice,
            Integer distance
    ) {
        return calculate(EmissionRequest.builder()
                .source(source)
                .fuelType(fuelType)
                .averageConsumption(averageComsumption)
                .fuelPrice(fuelPrice)
                .totalDistance(distance)
                .build());
    }

    EmissionResponse calculate(EmissionRequest request);

    static EmissionSyncAPI getInstance() {
        return new EmissionSyncApiImpl(EmissionAsyncAPI.getInstance());
    }

    static EmissionSyncAPI getInstance(Environment environment) {
        return  new EmissionSyncApiImpl(EmissionAsyncAPI.getInstance(environment));
    }
}
