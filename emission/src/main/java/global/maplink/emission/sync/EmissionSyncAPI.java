package global.maplink.emission.sync;

import global.maplink.emission.async.EmissionAsyncAPI;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.emission.schema.EmissionResponse;
import global.maplink.emission.schema.FractionedEmission;
import global.maplink.env.Environment;

import java.math.BigDecimal;
import java.util.List;

public interface EmissionSyncAPI {

    default EmissionResponse calculate(
            String source,
            String fuelType,
            BigDecimal autonomy,
            BigDecimal averageConsumption,
            BigDecimal fuelPrice,
            Integer distance,
            List<FractionedEmission> fractionedEmissions
    ) {
        return calculate(EmissionRequest.builder()
                .source(source)
                .fuelType(fuelType)
                .autonomy(autonomy)
                .averageConsumption(averageConsumption)
                .fuelPrice(fuelPrice)
                .totalDistance(distance)
                .fractionedEmissions(fractionedEmissions)
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
