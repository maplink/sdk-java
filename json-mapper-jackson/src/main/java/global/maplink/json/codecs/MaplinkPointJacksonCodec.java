package global.maplink.json.codecs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import global.maplink.domain.MaplinkPoint;
import global.maplink.domain.PointsMode;
import global.maplink.http.request.RequestBody;

import java.io.IOException;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

public class MaplinkPointJacksonCodec extends MlpJacksonCodec<MaplinkPoint> {

    public static final String FIELD_LATITUDE = "latitude";
    public static final String FIELD_LONGITUDE = "longitude";
    public static final String FIELD_POINT = "point";

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
                case SIMPLE:
                    serializeAsSimple(value, jgen);
                    break;
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

        private void serializeAsSimple(MaplinkPoint value, JsonGenerator jgen) throws IOException {
            jgen.writeStartObject();
            jgen.writeStringField(FIELD_POINT, format(ENGLISH, "%.7f,%7f", value.getLatitude(), value.getLongitude()));
            jgen.writeEndObject();
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
                    return deserializeObjectOrSimple(parser);
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

        private MaplinkPoint deserializeObjectOrSimple(JsonParser parser) throws IOException {
            JsonNode node = parser.readValueAsTree();
            if (node.has(FIELD_POINT)) {
                String value = node.get(FIELD_POINT).asText();
                String[] coordinates = value.split(",");

                if (coordinates.length != 2) {
                    throw new IllegalArgumentException("Invalid point format [" + value + "], expected [lat,long]");
                }

                return new MaplinkPoint(
                        parseDouble(coordinates[0]),
                        parseDouble(coordinates[1])
                );
            }

            return new MaplinkPoint(
                node.get(FIELD_LATITUDE).asDouble(),
                node.get(FIELD_LONGITUDE).asDouble()
            );

        }

    }

}
