package global.maplink.json.codecs;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import global.maplink.json.MaplinkSdkModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MlpJacksonCodec<T> {
    private final Class<T> type;
    private final JsonSerializer<T> serializer;
    private final JsonDeserializer<T> deserializer;

    public void register(MaplinkSdkModule module) {
        module.addSerializer(type, serializer);
        module.addDeserializer(type, deserializer);
    }
}
