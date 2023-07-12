package global.maplink.trip.schema.v2.problem;

import global.maplink.commons.TransponderOperator;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.TollVehicleType;
import global.maplink.trip.schema.v1.exception.violations.VariableAxlesOverlappingViolation;
import global.maplink.trip.schema.v1.exception.violations.VariableAxlesSiteIdNotFoundInProblem;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import static global.maplink.trip.schema.v1.exception.TripErrorType.TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE;
import static global.maplink.trip.schema.v1.exception.TripErrorType.VARIABLE_AXLES_FROMSITEID_POINTING_TO_LAST_SITE;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TollRequest implements Validable {
    private static final String SITE_NOT_USED = "site not used";
    private static final String SITE_USED_AS_FROM_SITE_ID = "site used as fromSiteId";
    private static final String SITE_USED_AS_TO_SITE_ID = "site used as toSiteId";
    private static final String SITE_USED_AS_MIDDLE_SITE = "site used as middle site";

    private final TollVehicleType vehicleType;
    @Builder.Default
    private final Billing billing = Billing.DEFAULT;
    @Builder.Default
    private final Set<TransponderOperator> transponderOperators = new HashSet<>(Collections.singletonList(TransponderOperator.SEM_PARAR));
    @Singular
    private final List<LegVariableAxles> variableAxles;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();
        if (vehicleType == null) {
            errors.add(TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE);
        }
        return errors;
    }

    public List<ValidationViolation> validateVariableAxles(final List<SitePoint> sites){
        List<ValidationViolation> errors = new ArrayList<>();

        if (variableAxles == null || sites.isEmpty()) {
            return errors;
        }

        List<String> problemSites = getProblemSites(sites);
        Map<String, String> sitesStatusMap = createVirginSitesStatusMap(problemSites);

        for (LegVariableAxles legVariableAxles : variableAxles) {
            String fromSiteId = legVariableAxles.getFromSiteId();
            String toSiteId = legVariableAxles.getToSiteId();

            errors.addAll(legVariableAxles.validate());

            if (isNull(fromSiteId) || isNull(toSiteId)) {
                return errors;
            }

            if (!problemSites.contains(fromSiteId)) {
                errors.add(new VariableAxlesSiteIdNotFoundInProblem(fromSiteId));
                return errors;
            }

            if (!problemSites.contains(toSiteId)) {
                errors.add(new VariableAxlesSiteIdNotFoundInProblem(toSiteId));
                return errors;
            }

            if (fromSiteId.equalsIgnoreCase(problemSites.get(problemSites.size() - 1))) {
                errors.add(VARIABLE_AXLES_FROMSITEID_POINTING_TO_LAST_SITE);
                return errors;
            }

            List<String> legSites = getLegSites(fromSiteId, toSiteId, problemSites);

            if (existsOverlap(fromSiteId, toSiteId, legSites, sitesStatusMap)) {
                errors.add(new VariableAxlesOverlappingViolation(fromSiteId, toSiteId));
                return errors;
            } else {
                sitesStatusMap.put(fromSiteId, SITE_USED_AS_FROM_SITE_ID);
                sitesStatusMap.put(toSiteId, SITE_USED_AS_TO_SITE_ID);
                for (int i = 1; i < legSites.size() - 1; i++) {
                    sitesStatusMap.put(legSites.get(i), SITE_USED_AS_MIDDLE_SITE);
                }
            }
        }
        return errors;
    }

    private List<String> getProblemSites(final List<SitePoint> sites) {

        return sites.stream()
                .filter(Objects::nonNull)
                .map(SitePoint::getSiteId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Map<String, String> createVirginSitesStatusMap(List<String> problemSites) {
        Map<String, String> map = new HashMap<>(problemSites.size());
        for (String site : problemSites) {
            map.put(site, SITE_NOT_USED);
        }
        return map;
    }

    private List<String> getLegSites(String fromSiteId, String toSiteId, List<String> problemSites) {
        return problemSites.subList(problemSites.indexOf(fromSiteId), problemSites.indexOf(toSiteId) + 1);
    }

    private boolean existsOverlap(
            String fromSiteId,
            String toSiteId,
            List<String> legSites,
            Map<String, String> sitesStatusMap
    ) {
        for (String site : legSites) {
            if (site.equalsIgnoreCase(fromSiteId) && sitesStatusMap.get(site)
                    .equalsIgnoreCase(SITE_USED_AS_MIDDLE_SITE)) {
                return true;
            }

            if (site.equalsIgnoreCase(toSiteId) && sitesStatusMap.get(site)
                    .equalsIgnoreCase(SITE_USED_AS_MIDDLE_SITE)) {
                return true;
            }

            if (site.equalsIgnoreCase(fromSiteId) && sitesStatusMap.get(site)
                    .equalsIgnoreCase(SITE_USED_AS_FROM_SITE_ID)) {
                return true;
            }

            if (!site.equalsIgnoreCase(fromSiteId) && !site.equalsIgnoreCase(toSiteId) && !sitesStatusMap.get(site)
                    .equalsIgnoreCase(SITE_NOT_USED)) {
                return true;
            }
        }
        return false;
    }

}
