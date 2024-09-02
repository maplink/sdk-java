package global.maplink.trip.schema.v2.features.turnByTurn;


import global.maplink.validations.ValidationViolation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static global.maplink.trip.schema.v1.exception.TripErrorType.TURN_BY_TURN_LANGUAGE_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TurnByTurnRequest {

    private final Languages language;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();
        if (language == null) {
            errors.add(TURN_BY_TURN_LANGUAGE_NOT_FOUND);
        }
        return errors;
    }
}
