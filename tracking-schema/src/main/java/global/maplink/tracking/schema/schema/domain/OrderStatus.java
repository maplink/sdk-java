package global.maplink.tracking.schema.schema.domain;


import lombok.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class OrderStatus {

    private final Value value;
    private final String label;
}
