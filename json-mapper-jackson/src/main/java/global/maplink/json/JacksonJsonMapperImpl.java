package global.maplink.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JacksonJsonMapperImpl implements JsonMapper {

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules().disable(FAIL_ON_UNKNOWN_PROPERTIES);

    @Override
    public <T> T fromJson(byte[] data, Class<T> type) {
        try {
            return mapper.readValue(data, type);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> List<T> fromJsonList(byte[] data, Class<T> type) {
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.readValue(data, collectionType);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> byte[] toJson(T object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
}
