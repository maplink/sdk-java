package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
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
public class Site {

    private final String name;
    private final Coordinates coordinates;
    private final String logisticConstraints;
    private final List<String> logisticZones;

    private final Coordinates coordinatesValidator;


    private final int maxSites;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("site.name"));
        }

        if(isNull(coordinates)){
            violations.add(PlanningUpdateViolation.of("site.coordinates"));
        }

        if(FieldValidator.isInvalid(logisticConstraints)){
            violations.add(PlanningUpdateViolation.of("site.coordinates"));
        }

        coordinatesValidator.validate(violations, coordinates);

        return violations;
    }

    public void validateSitesLimit(List<String> errors, List<Site> sites, List<Site> depots) {
        final int totalOfSites = sites.size() + depots.size();
        if (totalOfSites > maxSites) {
            errors.add("The total of points (sites and depots) cannot exceed " + maxSites);
        }
    }
}
