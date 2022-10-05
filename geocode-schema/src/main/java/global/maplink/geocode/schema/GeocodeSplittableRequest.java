package global.maplink.geocode.schema;

import java.util.List;

public interface GeocodeSplittableRequest extends GeocodeServiceRequest{

    List<? extends GeocodeSplittableRequest> split();
}
