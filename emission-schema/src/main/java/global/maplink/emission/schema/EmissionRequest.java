package global.maplink.emission.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static global.maplink.http.request.Request.put;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public final class EmissionRequest implements MapLinkServiceRequest<EmissionResponse> {
    public static final String PATH = "emission/v1/calculations";

    private final String source;
    private final String fuelType;
    private final BigDecimal autonomy;
    private final BigDecimal averageConsumption;
    private final BigDecimal fuelPrice;
    private final Integer totalDistance;
    private final List<FractionedEmission> fractionedEmissions;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return put(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    @Override
    public Function<Response, EmissionResponse> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, EmissionResponse.class);
    }

    public boolean isValid() {
        return getTotalDistance() != null &&
                (!isNull(getAutonomy()) || !isNull(getAverageConsumption()))
                && !isNull(getFuelType());
    }
}
