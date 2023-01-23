package global.maplink.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import global.maplink.json.codecs.MaplinkPointJacksonCodec;
import global.maplink.json.codecs.MaplinkPointsJacksonCodec;
import global.maplink.json.codecs.MlpJacksonCodec;

public class MaplinkSdkModule extends SimpleModule {

    public MaplinkSdkModule() {
        this(
                new MaplinkPointsJacksonCodec(),
                new MaplinkPointJacksonCodec()
        );
    }

    public MaplinkSdkModule(MlpJacksonCodec<?>... codecs) {
        for (MlpJacksonCodec<?> codec : codecs) {
            codec.register(this);
        }
    }
}
