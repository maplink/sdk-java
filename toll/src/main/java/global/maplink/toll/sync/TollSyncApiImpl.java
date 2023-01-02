package global.maplink.toll.sync;

import global.maplink.toll.async.TollAsyncAPI;
import global.maplink.toll.schema.request.TollCalculationRequest;
import global.maplink.toll.schema.result.TollCalculationResult;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public class TollSyncApiImpl implements TollSyncAPI {

    private final TollAsyncAPI delegate;

    @Override
    public TollCalculationResult calculate(TollCalculationRequest request) {
        return await(delegate.calculate(request));
    }

}
