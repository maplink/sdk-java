package global.maplink.token;

import global.maplink.http.request.Request;

public interface MapLinkToken {
    boolean isExpired();

    boolean isAboutToExpireIn(int seconds);

    Request applyOn(Request request);
}
