package global.maplink.freight.sync;

import global.maplink.freight.async.FreightAsyncAPI;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.freight.schema.FreightCalculationResponse;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public class FreightSyncApiImpl implements FreightSyncAPI {

    private final FreightAsyncAPI delegate;

    @Override
    public FreightCalculationResponse calculate(FreightCalculationRequest request) {
        return await(delegate.calculate(request));
    }
}
