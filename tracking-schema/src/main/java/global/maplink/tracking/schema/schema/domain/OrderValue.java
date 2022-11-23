package global.maplink.tracking.schema.schema.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class OrderValue {

    private final BigDecimal value;
    private final String currency;
}
