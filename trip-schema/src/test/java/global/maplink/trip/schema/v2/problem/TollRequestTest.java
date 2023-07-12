package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.TollVehicleType;
import global.maplink.trip.schema.v1.exception.TripErrorType;
import global.maplink.validations.ValidationViolation;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static global.maplink.commons.TransponderOperator.CONECTCAR;
import static global.maplink.commons.TransponderOperator.SEM_PARAR;
import static global.maplink.trip.testUtils.ProblemSampleFiles.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();
    private List<ValidationViolation> errors;
    private List<SitePoint> sites;
    private List<LegVariableAxles> variableAxles;

    @BeforeEach
    public void init() {
        errors = new ArrayList<>();
    }

    @Test
    public void shouldDeserialize() {
        TollRequest tollRequest = mapper.fromJson(TOLL_REQUEST_CONECTCAR.load(), TollRequest.class);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(Billing.FREE_FLOW, tollRequest.getBilling());
        assertEquals(new HashSet<>(Collections.singletonList(CONECTCAR)), tollRequest.getTransponderOperators());
    }

    @Test
    public void shouldDeserializeWithDefaultTransponderOperator() {
        TollRequest tollRequest = mapper.fromJson(TOLL_REQUEST.load(), TollRequest.class);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(Billing.FREE_FLOW, tollRequest.getBilling());
        assertEquals(new HashSet<>(Collections.singletonList(SEM_PARAR)), tollRequest.getTransponderOperators());
    }

    @Test
    public void should_pass_for_valid_variableAxles() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(0);
    }

    @Test
    public void should_fail_on_siteId_not_in_trip_problem() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        variableAxles.get(0).setFromSiteId("INVALID SITEID");
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("siteId INVALID SITEID not found in trip problem sites.");
    }

    @Test
    public void should_fail_on_missing_fromSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        variableAxles.get(0).setFromSiteId(null);
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).contains(TripErrorType.VARIABLE_AXLES_FROMSITEID_EMPTY.getMessage());
    }

    @Test
    public void should_fail_on_missing_toSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        variableAxles.get(0).setToSiteId(null);
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).contains(TripErrorType.VARIABLE_AXLES_TOSITEID_EMPTY.getMessage());
    }

    @Test
    public void should_fail_on_fromSiteId_equal_to_toSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        variableAxles.get(0).setToSiteId(variableAxles.get(0).getFromSiteId());
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("fromSiteId JUNDIAI and toSiteId JUNDIAI cannot be the same");
    }

    @Test
    public void should_fail_on_lastSite_as_fromSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        variableAxles.get(0).setFromSiteId(sites.get(sites.size()-1).getSiteId());
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).contains(TripErrorType.VARIABLE_AXLES_FROMSITEID_POINTING_TO_LAST_SITE.getMessage());
    }

    @Test
    public void should_detect_Overlap_on_variableAxles() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES_OVERLAPPING.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("VariableAxles leg overlap found on leg from CAMPINAS to SÃO CARLOS.");
    }

    @Test
    public void should_detect_Overlap_on_variableAxles_when_leg_ends_on_middle() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES_OVERLAPPING_ENDING_ON_MIDDLE.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("VariableAxles leg overlap found on leg from SP to CAMPINAS.");
    }

    @Test
    public void should_detect_Overlap_on_variableAxles_when_middle_overlapping() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES_OVERLAPPING_MIDDLE.load(), TripProblem.class);
        sites = problem.getPoints();
        val toll = problem.getToll();
        variableAxles = toll.getVariableAxles();
        errors = toll.validateVariableAxles(sites);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("VariableAxles leg overlap found on leg from SP to MATÃO.");
    }

}
