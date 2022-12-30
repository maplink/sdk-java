package global.maplink.emission.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static global.maplink.http.request.Request.put;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public final class EmissionRequest implements MapLinkServiceRequest {
    public static final String PATH = "emission/v1/calculations";

    private final Source source;
    private final Fuel fuelType;
    private final BigDecimal averageConsumption;
    private final BigDecimal fuelPrice;
    private final Integer totalDistance;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return put(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

}
