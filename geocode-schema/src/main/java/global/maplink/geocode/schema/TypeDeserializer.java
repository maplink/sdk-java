package global.maplink.geocode.schema;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import global.maplink.geocode.schema.v1.TypeVersionOne;
import global.maplink.geocode.schema.v2.TypeVersionTwo;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<Type> {
    @Override
    public Type deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String typeName = jsonParser.getText();
        for (TypeVersionOne type : TypeVersionOne.values()) {
            if (type.name().equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        for (TypeVersionTwo type : TypeVersionTwo.values()) {
            if (type.name().equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknow type: " + typeName);
    }
}
