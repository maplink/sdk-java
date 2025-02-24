package global.maplink.geocode.schema.v1;

import java.util.List;

public interface GeocodeSplittableRequest extends GeocodeServiceRequest {

    List<? extends GeocodeSplittableRequest> split();
}
