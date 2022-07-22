package global.maplink.credentials;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ProvidedMapLinkCredentials implements MapLinkCredentials {
    private final String token;
}
