package global.maplink.toll.schema.request;

import global.maplink.MapLinkServiceRequest;
import global.maplink.commons.TransponderOperator;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.result.TollCalculationResult;
import lombok.*;

import java.util.*;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TollCalculationRequest implements MapLinkServiceRequest<TollCalculationResult> {
    public static final String PATH = "toll/v1/calculations";

    @Singular
    private final List<LegRequest> legs;

    @Builder.Default
    private final Billing billing = Billing.DEFAULT;

    @Builder.Default
    private final Set<TransponderOperator> transponderOperators = new HashSet<>(Collections.singletonList(TransponderOperator.SEM_PARAR));

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    @Override
    public Function<Response, TollCalculationResult> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, TollCalculationResult.class);
    }
}
