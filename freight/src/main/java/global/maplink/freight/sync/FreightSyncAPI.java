package global.maplink.freight.sync;

import global.maplink.env.Environment;
import global.maplink.freight.async.FreightAsyncAPI;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.freight.schema.FreightCalculationResponse;

public interface FreightSyncAPI {

    FreightCalculationResponse calculate(FreightCalculationRequest request);

    static FreightSyncAPI getInstance() {
        return new FreightSyncApiImpl(FreightAsyncAPI.getInstance());
    }

    static FreightSyncAPI getInstance(Environment environment) {
        return new FreightSyncApiImpl(FreightAsyncAPI.getInstance(environment));
    }
}
