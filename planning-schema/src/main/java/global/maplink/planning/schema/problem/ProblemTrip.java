package global.maplink.planning.schema.problem;

import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.trip.schema.v1.exception.TripCalculationRequestException;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.problem.TollRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static global.maplink.trip.schema.v1.exception.TripErrorType.ROUTE_POINTS_LESS_THAN_TWO;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class ProblemTrip {

    private static final int MINIMUM_POINTS = 2;
    private final String profileName;
    private final CalculationMode calculationMode;
    private final Long startDate;
    private final TollRequest toll;
    private final CrossedBordersRequest crossedBoarders;
    private final FreightCalculationRequest freight;

    public void isValid(Problem problem){
        validateWithPoints(problem.getSites(), problem.getDepots());
        validatePointsEquality(problem.getSites(), problem.getDepots());
    }

    private void validateWithPoints(List<Site> sites,List<Site> depots){
        var points = Stream.concat(sites.stream(), depots.stream()).count();
        if(points < MINIMUM_POINTS){
            throw new TripCalculationRequestException(ROUTE_POINTS_LESS_THAN_TWO);
        }
    }

    private void validatePointsEquality(List<Site> sites, List<Site> depots){
        if(checkPointsEquality(sites, depots)){
            throw new TripCalculationRequestException(ROUTE_POINTS_LESS_THAN_TWO);
        }
    }

    private boolean checkPointsEquality(List<Site> sites, List<Site> depots){
        var allCoordinates = Stream.concat(sites.stream().map(Site::getCoordinates),depots.stream().map(Site::getCoordinates)).collect(
                Collectors.toList());

        for(int i = 0; i < allCoordinates.size() - 1; i++){
            if(!allCoordinates.get(i).equals(allCoordinates.get(i+1))){
                return false;
            }
        }
        return true;
    }
}
