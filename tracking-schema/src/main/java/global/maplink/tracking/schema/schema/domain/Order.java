package global.maplink.tracking.schema.schema.domain;

import global.maplink.geocode.schema.Address;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Order {

    private final String id;
    private final Long number;
    private final String description;
    private final LocalDateTime estimatedArrival;
    private final String companyName;
    private final OrderValue totalValue;
    private final OrderStatus status;
    private final Address origin;
    private final Address destination;
    private final Driver driver;
    private final Audit audit;
    private final Instant expiresIn;
    private final Theme theme;

}