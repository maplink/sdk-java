package global.maplink.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class MaplinkPolygon {
    private final String name;
    private final MaplinkPoints vertices;
}
