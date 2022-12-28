package global.maplink.toll.async;

import global.maplink.toll.schema.request.TollCalculationRequest;
import global.maplink.toll.schema.result.TollCalculationResult;

import java.util.concurrent.CompletableFuture;

public interface TollAsyncAPI {

    CompletableFuture<TollCalculationResult> calculate(TollCalculationRequest request);

}
