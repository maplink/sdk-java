package global.maplink.geocode.schema.v1.reverse;


import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ReverseRequest extends ReverseBaseRequest {
    public static final String PATH = "geocode/v1/reverse";
    public static final int ENTRY_LIMIT = 200;

    public static ReverseRequest of(List<ReverseBaseRequest.Entry> entries) {
        return ReverseRequest.builder()
                .entries(entries)
                .build();
    }


    @Override
    protected int entryLimit() {
        return ENTRY_LIMIT;
    }

    @Override
    protected String path() {
        return PATH;
    }
}
