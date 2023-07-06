package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static global.maplink.trip.testUtils.ProblemSampleFiles.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VariableAxlesValidatorTest {

    private VariableAxlesValidator validator;
    private List<String> errors;
    private List<SitePoint> sites;
    private List<LegVariableAxles> variableAxles;
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @BeforeEach
    public void init() {
        validator = new VariableAxlesValidator();
        errors = new ArrayList<>();
    }

    @Test
    public void should_pass_on_checkVariableAxles() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(0);
    }

    @Test
    public void should_fail_on_missing_fromSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        variableAxles.get(0).setFromSiteId(null);
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("fromSiteId may not be empty");
    }

    @Test
    public void should_fail_on_missing_toSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        variableAxles.get(0).setToSiteId(null);
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("toSiteId may not be empty");
    }

    @Test
    public void should_fail_on_fromSiteId_equal_to_toSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        variableAxles.get(0).setToSiteId(variableAxles.get(0).getFromSiteId());
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("fromSiteId and toSiteId cannot be the same");
    }

    @Test
    public void should_fail_on_lastSite_as_fromSiteId() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        variableAxles.get(0).setFromSiteId(sites.get(sites.size()-1).getSiteId());
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("fromSiteId cannot be equal to the last site in trip problem (MATÃO).");
    }

    @Test
    public void should_detect_Overlap_on_checkVariableAxles() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES_OVERLAPPING.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("VariableAxles leg overlap found on");
    }

    @Test
    public void should_detect_Overlap_on_checkVariableAxles_when_leg_ends_on_middle() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES_OVERLAPPING_ENDING_ON_MIDDLE.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("VariableAxles leg overlap found on");
    }

    @Test
    public void should_detect_Overlap_on_checkVariableAxles_when_middle_overlapping() {
        TripProblem problem = mapper.fromJson(PROBLEM_VARIABLE_AXLES_OVERLAPPING_MIDDLE.load(), TripProblem.class);
        sites = problem.getPoints();
        variableAxles = problem.getToll().getVariableAxles();
        validator.checkVariableAxles(errors, sites, variableAxles);

        assertThat(errors.size()).isEqualTo(1);
        assertThat(errors.get(0)).contains("VariableAxles leg overlap found on");
    }

}
