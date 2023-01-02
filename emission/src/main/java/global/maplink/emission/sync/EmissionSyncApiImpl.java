package global.maplink.emission.sync;

import global.maplink.emission.async.EmissionAsyncAPI;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.emission.schema.EmissionResponse;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public class EmissionSyncApiImpl implements EmissionSyncAPI {

    private final EmissionAsyncAPI delegate;

    @Override
    public EmissionResponse calculate(EmissionRequest request) {
        return await(delegate.calculate(request));
    }
}
