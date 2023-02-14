package global.maplink.freight.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.freight.schema.exception.FreightErrorType;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class FreightCalculationRequest implements MapLinkServiceRequest<FreightCalculationResponse>{
    public static final String PATH = "freight/v1/calculations";

    private final Set<OperationType> operationType;
    private final Set<GoodsType> goodsType;
    private final Set<Integer> axis;
    private final List<AdditionalCosts> otherCosts;
    private final BigDecimal distance;
    private final LocalDate date;
    private final boolean roundTrip;
    private final boolean backEmpty;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    @Override
    public Function<Response, FreightCalculationResponse> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, FreightCalculationResponse.class);
    }

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        if (date == null) {
            errors.add(FreightErrorType.DATE_FIELD_EMPTY);
        }

        if (operationType == null || operationType.isEmpty()) {
            errors.add(FreightErrorType.OPERATION_TYPE_FIELD_EMPTY);
        }

        if (goodsType == null || goodsType.isEmpty()) {
            errors.add(FreightErrorType.GOODS_TYPE_FIELD_EMPTY);
        }

        if (axis == null || axis.isEmpty()) {
            errors.add(FreightErrorType.AXIS_FIELD_EMPTY);
        }

        return errors;
    }
}