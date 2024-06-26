package global.maplink.planning.schema.problem;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Callback {

    private final String jobId;
    private final String url;
    private final String user;
    private final String password;
}
