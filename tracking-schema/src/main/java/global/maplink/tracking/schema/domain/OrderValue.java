package global.maplink.tracking.schema.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class OrderValue {

    private final BigDecimal value;
    private final String currency;
}
