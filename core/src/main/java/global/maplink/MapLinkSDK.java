package global.maplink;

import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MapLinkSDK {
    private static MapLinkSDK INSTANCE = null;

    private final HttpAsyncEngine http;

    private final Environment environment;

    private final JsonMapper jsonMapper;


    private MapLinkSDK() {
        http = HttpAsyncEngine.loadDefault();
        environment = Environment.loadDefault();
        jsonMapper = JsonMapper.loadDefault();
    }


    public static MapLinkSDK getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MapLinkSDK();
        }
        return INSTANCE;
    }
}
