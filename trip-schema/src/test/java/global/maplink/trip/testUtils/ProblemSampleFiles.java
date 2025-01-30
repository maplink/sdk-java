package global.maplink.trip.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum ProblemSampleFiles {
    CROSSED_BORDERS_REQUEST("trip/v2/problem/json/crossedBordersRequest.json"),
    SITE_POINT("trip/v2/problem/json/sitePoint.json"),
    TOLL_REQUEST("trip/v2/problem/json/tollRequest.json"),
    TOLL_REQUEST_CONECTCAR("trip/v2/problem/json/tollRequest_Conectcar.json"),
    TRIP_REQUEST("trip/v2/problem/json/tripRequest.json"),
    TRIP_REQUEST_DEFAULT("trip/v2/problem/json/tripRequestCalculationModeDefault.json"),
    PROBLEM_VARIABLE_AXLES("trip/v2/problem/json/problem_VariableAxles.json"),
    PROBLEM_MISSING_VARIABLE_AXLES("trip/v2/problem/json/problem_Missing_VariableAxles.json"),
    PROBLEM_VARIABLE_AXLES_OVERLAPPING("trip/v2/problem/json/problem_VariableAxles_Overlapping.json"),
    PROBLEM_VARIABLE_AXLES_OVERLAPPING_ENDING_ON_MIDDLE("trip/v2/problem/json/problem_VariableAxles_Overlapping_Ending_on_Middle.json"),
    PROBLEM_VARIABLE_AXLES_OVERLAPPING_MIDDLE("trip/v2/problem/json/problem_VariableAxles_Overlapping_Middle.json"),
    TRIP_REQUEST_THE_SHORTEST("trip/v2/problem/json/tripRequestCalculationModeTheShortest.json"),
    PROBLEM_WITH_VEHICLE_TYPE("trip/v2/problem/json/problem_with_vehicle_type.json"),
    PROBLEM_WITH_TOLL_AND_VEHICLE_TYPE("trip/v2/problem/json/problem_with_toll_and_vehicle_type.json"),
    PROBLEM_WITH_SINGLE_POINT("trip/v2/problem/json/problem_with_single_point.json");

    private final String filePath;

    public byte[] load(){
        URL resource = ProblemSampleFiles.class.getClassLoader().getResource(filePath);
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}