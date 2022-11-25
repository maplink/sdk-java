package global.maplink.tracking.schema.schema.domain;


import lombok.*;


import java.time.Instant;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Audit {

    private final Instant createdAt;
    private final Instant updatedAt;
}
