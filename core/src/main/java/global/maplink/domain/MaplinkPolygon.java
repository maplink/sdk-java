package global.maplink.domain;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class MaplinkPolygon {
    private final String name;
    @Singular
    private final List<MaplinkPoints> vertices;
}
