package global.maplink.trip.schema.v1.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Callback {
    private String url;
    private String user;
    private String password;
}
