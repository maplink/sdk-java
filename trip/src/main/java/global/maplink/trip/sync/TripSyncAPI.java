package global.maplink.trip.sync;

import global.maplink.env.Environment;
import global.maplink.trip.async.TripAsyncAPI;
import global.maplink.trip.schema.v2.problem.TripCalculateRequest;
import global.maplink.trip.schema.v2.solution.TripSolution;

public interface TripSyncAPI {

    TripSolution calculate(TripCalculateRequest request);

    static TripSyncAPI getInstance() {
        return new TripSyncApiImpl(TripAsyncAPI.getInstance());
    }

    static TripSyncAPI getInstance(Environment environment) {
        return new TripSyncApiImpl(TripAsyncAPI.getInstance(environment));
    }
}
