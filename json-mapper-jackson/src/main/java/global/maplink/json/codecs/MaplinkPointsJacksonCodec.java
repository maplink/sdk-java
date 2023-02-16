package global.maplink.json.codecs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import global.maplink.domain.MaplinkPoint;
import global.maplink.domain.MaplinkPoints;
import global.maplink.domain.PointsMode;

import java.io.IOException;

import static java.util.Objects.isNull;

public class MaplinkPointsJacksonCodec extends MlpJacksonCodec<MaplinkPoints> {

    public MaplinkPointsJacksonCodec() {
        super(MaplinkPoints.class, new Serializer(), new Deserializer());
    }

    public static class Serializer extends JsonSerializer<MaplinkPoints> {

        @Override
        public void serialize(
                MaplinkPoints value,
                JsonGenerator jgen,
                SerializerProvider provider
        ) throws IOException {
            switch (PointsMode.current()) {
                case SIMPLE:
                case OBJECT:
                case ARRAY:
                case GEOHASH:
                    serializeAsList(value, jgen);
                    break;
                case POLYLINE:
                    serializeAsPolyline(value, jgen);
                    break;
            }
        }

        private void serializeAsList(
                MaplinkPoints value,
                JsonGenerator jgen
        ) throws IOException {
            jgen.writeStartArray();
            for (MaplinkPoint point : value) {
                jgen.writePOJO(point);
            }
            jgen.writeEndArray();
        }

        private void serializeAsPolyline(
                MaplinkPoints value,
                JsonGenerator jgen
        ) throws IOException {
            jgen.writeString(value.toPolyline());
        }
    }

    public static class Deserializer extends JsonDeserializer<MaplinkPoints> {

        @Override
        public MaplinkPoints deserialize(
                JsonParser parser,
                DeserializationContext context
        ) throws IOException {
            switch (parser.currentToken()) {
                case VALUE_STRING:
                    return deserializePolyline(parser);
                case START_ARRAY:
                    return deserializeArray(parser);
                default:
                    return null;
            }
        }

        private MaplinkPoints deserializePolyline(JsonParser parser) throws IOException {
            return MaplinkPoints.fromPolyline(parser.readValueAs(String.class));
        }

        private MaplinkPoints deserializeArray(JsonParser parser) throws IOException {
            MaplinkPoint[] points = parser.readValueAs(MaplinkPoint[].class);
            if (isNull(points) || points.length < 1) {
                return MaplinkPoints.EMPTY;
            }
            return new MaplinkPoints(points);
        }
    }

}
