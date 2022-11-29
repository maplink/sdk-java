package global.maplink.tracking.schema.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Audit {

    private final Instant createdAt;
    private final Instant updatedAt;
}
