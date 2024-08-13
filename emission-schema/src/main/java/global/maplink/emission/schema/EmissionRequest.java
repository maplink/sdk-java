package global.maplink.emission.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.emission.schema.exception.EmissionViolation;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationViolation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static global.maplink.emission.schema.exception.EmissionErrorType.*;
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
    private final List<FractionedEmission> fractionedEmission;

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

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if(isNull(totalDistance)) {
            violations.add(EmissionViolation.of("emission.totalDistance", REQUIRED_FIELDS_IS_NULL));
        } else if (totalDistance < 0){
            violations.add(EmissionViolation.of("emission.totalDistance", TOTAL_DISTANCE_NEGATIVE));
        }

        if(isNull(fuelType)){
            violations.add(EmissionViolation.of("emission.fuelType", REQUIRED_FIELDS_IS_NULL));
        }

        if(isNull(autonomy) && isNull(averageConsumption)){
            violations.add(EmissionViolation.of("emission.autonomyOrAverageConsumption", REQUIRED_FIELDS_IS_NULL));
        }

        if(!isNull(fractionedEmission)){
            int sum = 0;
            for(FractionedEmission index : fractionedEmission){
                sum += index.getPercentage();
            }
            if(sum > 100){
                violations.add(EmissionViolation.of("emission.fractionedEmission", FRACTIONED_EMISSION_BIGGER_THAN_100));
            }
        }
        return violations;
    }
}
