package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
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

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(isNull(url)){
            violations.add(PlanningUpdateViolation.of("Callback.url"));
        }

        validateURL(violations);

        return violations;
    }

    private void validateURL(List<ValidationViolation> violations) {
        final String REGEX_URL_PATTERN = "(https?://[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|"
                + "(https?://)?localhost(:\\d+)?(/[a-zA-Z0-9]+)*/?)";

        if(isNull(url)){
            return;
        }

        if(!url.matches(REGEX_URL_PATTERN)){
            violations.add(PlanningUpdateViolation.of("Callback.urlDoesNotMatchPattern"));
        }
    }
}
