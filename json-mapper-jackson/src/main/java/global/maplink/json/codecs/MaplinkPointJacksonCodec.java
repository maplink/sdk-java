package global.maplink.json.codecs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import global.maplink.domain.MaplinkPoint;
import global.maplink.domain.PointsMode;

import java.io.IOException;

public class MaplinkPointJacksonCodec extends MlpJacksonCodec<MaplinkPoint> {

    public static final String FIELD_LATITUDE = "latitude";
    public static final String FIELD_LONGITUDE = "longitude";

    public MaplinkPointJacksonCodec() {
        super(MaplinkPoint.class, new Serializer(), new Deserializer());
    }

    public static class Serializer extends JsonSerializer<MaplinkPoint> {

        @Override
        public void serialize(
                MaplinkPoint value,
                JsonGenerator jgen,
                SerializerProvider provider
        ) throws IOException {
            switch (PointsMode.current()) {
                case ARRAY:
                    serializeAsArray(value, jgen);
                    break;
                case GEOHASH:
                    serializeAsGeohash(value, jgen);
                    break;
                case OBJECT:
                case POLYLINE:
                    serializeAsObject(value, jgen);
                    break;
            }
        }

        private void serializeAsObject(MaplinkPoint value, JsonGenerator jgen) throws IOException {
            jgen.writeStartObject();
            jgen.writeNumberField(FIELD_LATITUDE, value.getLatitude());
            jgen.writeNumberField(FIELD_LONGITUDE, value.getLongitude());
            jgen.writeEndObject();
        }

        private void serializeAsGeohash(MaplinkPoint value, JsonGenerator jgen) throws IOException {
            jgen.writeString(value.toGeohash());
        }

        private void serializeAsArray(MaplinkPoint value, JsonGenerator jgen) throws IOException {
            double[] coordinates = value.toArray();
            jgen.writeArray(coordinates, 0, coordinates.length);
        }

    }

    public static class Deserializer extends JsonDeserializer<MaplinkPoint> {

        @Override
        public MaplinkPoint deserialize(
                JsonParser parser,
                DeserializationContext context
        ) throws IOException {
            switch (parser.currentToken()) {
                case VALUE_STRING:
                    return deserializeGeohash(parser);
                case START_ARRAY:
                    return deserializeArray(parser);
                case START_OBJECT:
                    return deserializeObject(parser);
                default:
                    return null;
            }
        }

        private MaplinkPoint deserializeArray(JsonParser parser) throws IOException {
            double[] coordinates = parser.readValueAs(double[].class);
            return MaplinkPoint.from(coordinates);
        }

        private MaplinkPoint deserializeGeohash(JsonParser parser) throws IOException {
            String coordinates = parser.readValueAs(String.class);
            return MaplinkPoint.fromGeohash(coordinates);
        }

        private MaplinkPoint deserializeObject(JsonParser parser) throws IOException {
            JsonNode node = parser.readValueAsTree();
            return new MaplinkPoint(
                    node.get(FIELD_LATITUDE).asDouble(),
                    node.get(FIELD_LONGITUDE).asDouble()
            );
        }

    }

}
