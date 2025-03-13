package global.maplink.geocode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GeocodeVersion {

    V1("geocode/v1"),
    V2("geocode/v2");

    private final String basePath;

    public String apply(String subPath) {
        return basePath + subPath;
    }

}
