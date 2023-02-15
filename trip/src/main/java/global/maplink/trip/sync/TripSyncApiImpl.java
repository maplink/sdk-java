package global.maplink.trip.sync;

import global.maplink.trip.async.TripAsyncAPI;
import global.maplink.trip.schema.v2.problem.TripCalculateRequest;
import global.maplink.trip.schema.v2.solution.TripSolution;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;

@RequiredArgsConstructor
public class TripSyncApiImpl implements TripSyncAPI {
    private final TripAsyncAPI delegate;

    @Override
    public TripSolution calculate(TripCalculateRequest request) {
        return await(delegate.calculate(request));
    }

}
