package global.maplink.geocode.schema;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = TypeDeserializer.class)
public interface Type {
    String name();

}


