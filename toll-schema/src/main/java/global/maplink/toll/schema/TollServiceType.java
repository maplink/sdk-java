package global.maplink.toll.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TollServiceType {
    private final String serviceId;
    private final String name;
}
