package global.maplink.freight.schema;

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
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static global.maplink.http.request.Request.post;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class FreightCalculationRequest implements MapLinkServiceRequest {
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
}
