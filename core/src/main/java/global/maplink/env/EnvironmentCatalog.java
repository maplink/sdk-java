package global.maplink.env;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnvironmentCatalog implements Environment {
    PRODUCTION("https://api.maplink.global"),
    HOMOLOG("https://pre-api.maplink.global");

    private final String host;

}
