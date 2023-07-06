package global.maplink.trip.schema.v2.problem;

import global.maplink.toll.schema.TollVehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

//@AllArgsConstructor
@NoArgsConstructor
@Data
public class VariableAxlesValidator {
    private static final String MSG_MINIMIUM_SITEIDS = "need to have at least two siteIds: fromSiteId and toSiteId";
    private static final String MSG_BAD_SITEID = "siteId not found in trip-api problem";
    private static final String SITE_NOT_USED = "site not used";
    private static final String SITE_USED_AS_FROM_SITE_ID = "site used as fromSiteId";
    private static final String SITE_USED_AS_TO_SITE_ID = "site used as toSiteId";
    private static final String SITE_USED_AS_MIDDLE_SITE = "site used as middle site";
    private static final String MSG_SITEID_NOT_FOUND_IN_PROBLEM = "SiteID not found in trip problem sites";
    private static final String REG_VALUES = "VALUES";
    private static final String MSG_CONTAINED_IN = "does not have a valid value. Allowed values: VALUES";
    private static final String[] TOLL_VEHICLE_TYPES = Arrays.stream(TollVehicleType.values())
            .map(Enum::name)
            .toArray(String[]::new);

    public void checkVariableAxles(List<String> errors, List<SitePoint> sites, List<LegVariableAxles> variableAxles) {

        if (variableAxles == null || sites.isEmpty()) {
            return;
        }

        List<String> problemSites = getProblemSites(sites);
        Map<String, String> sitesStatusMap = createVirginSitesStatusMap(problemSites);

        for (LegVariableAxles legVariableAxles : variableAxles) {

            String fromSiteId = legVariableAxles.getFromSiteId();
            String toSiteId = legVariableAxles.getToSiteId();

            checkNotNull(errors, "fromSiteId", fromSiteId);
            checkNotNull(errors, "toSiteId", toSiteId);

            if (fromSiteId == null || toSiteId == null) {
                return;
            }

            checkContains(errors, "fromSiteId", fromSiteId, problemSites);
            checkContains(errors, "toSiteId", toSiteId, problemSites);

            if (fromSiteId.equalsIgnoreCase(problemSites.get(problemSites.size() - 1))) {
                errors.add("fromSiteId cannot be equal to the last site in trip problem (" + fromSiteId + ").");
                return;
            }

            if (fromSiteId.equalsIgnoreCase(toSiteId)) {
                errors.add("fromSiteId and toSiteId cannot be the same (" + fromSiteId + ").");
            }

            checkEnum(errors, "newVehicleType",
                    String.valueOf(legVariableAxles.getNewVehicleType()), TOLL_VEHICLE_TYPES
            );

            List<String> legSites = getLegSites(fromSiteId, toSiteId, problemSites);

            if (existsOverlap(fromSiteId, toSiteId, legSites, sitesStatusMap)) {
                errors.add("VariableAxles leg overlap found on ( " + fromSiteId + " to " + toSiteId + " ).");
            } else {
                sitesStatusMap.put(fromSiteId, SITE_USED_AS_FROM_SITE_ID);
                sitesStatusMap.put(toSiteId, SITE_USED_AS_TO_SITE_ID);
                for (int i = 1; i < legSites.size() - 1; i++) {
                    sitesStatusMap.put(legSites.get(i), SITE_USED_AS_MIDDLE_SITE);
                }
            }
        }

    }

    void checkNotNull(List<String> errors, String fieldName, Object fieldValue) {
        if (isNull(fieldValue)) {
            errors.add(fieldName + " " + "may not be empty");
        }
    }

    public void checkContains(List<String> errors, String fieldName, String value, List<String> list) {
        if (value == null) {
            return;
        }
        if (!list.contains(value)) {
            errors.add(fieldName + " " + MSG_SITEID_NOT_FOUND_IN_PROBLEM);
        }
    }

    public void checkEnum(List<String> errors, String fieldName, String value, String... possibilities) {
        if (value == null) {
            return;
        }
        List<String> listPossibilities = Arrays.asList(possibilities);
        if (!listPossibilities.contains(value)) {
            errors.add(fieldName + " " + MSG_CONTAINED_IN.replaceFirst(REG_VALUES, listPossibilities.toString()));
        }
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

    private List<String> getProblemSites(final List<SitePoint> sites) {
        return sites.stream()
                .filter(sp -> sp != null && sp.getSiteId() != null)
                .map(SitePoint::getSiteId)
                .collect(Collectors.toList());
    }

    private Map<String, String> createVirginSitesStatusMap(List<String> problemSites) {
        Map<String, String> map = new HashMap<>();
        for (String site : problemSites) {
            map.put(site, SITE_NOT_USED);
        }
        return map;
    }

    private List<String> getLegSites(String fromSiteId, String toSiteId, List<String> problemSites) {
        return problemSites.subList(problemSites.indexOf(fromSiteId), problemSites.indexOf(toSiteId) + 1);
    }

}