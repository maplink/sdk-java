package global.maplink.extensions;

import global.maplink.MapLinkServiceRequest;
import global.maplink.http.request.Request;

public interface SdkRequestFilterExtension extends SdkExtension {

    <T> Request filter(MapLinkServiceRequest<T> serviceRequest, Request request);

}
