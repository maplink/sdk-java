package global.maplink.toll.schema.request;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.Source;
import lombok.*;

import java.util.List;

import static global.maplink.http.request.Request.post;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TollCalculationRequest implements MapLinkServiceRequest {
    public static final String PATH = "toll/v1/calculations";

    @Singular
    private final List<LegRequest> legs;

    @Builder.Default
    private final Source source = Source.DEFAULT;

    @Builder.Default
    private final Billing billing = Billing.DEFAULT;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

}
