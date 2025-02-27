package global.maplink.geocode.schema.v1;

import com.fasterxml.jackson.annotation.JsonTypeName;
import global.maplink.geocode.schema.Type;

@JsonTypeName("TypeVersionOne")
public enum TypeVersionOne implements Type {
    ZIPCODE,
    STATE,
    CITY,
    POI,
    DISTRICT,
    ROAD,
    KM,
    POINTS
}
