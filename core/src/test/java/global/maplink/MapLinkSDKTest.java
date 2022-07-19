package global.maplink;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapLinkSDKTest {
    @Test
    public void mustFailOnInitialize() {
        assertThatThrownBy(MapLinkSDK::getInstance)
                .isInstanceOf(NoImplementationFoundException.class);
    }
}
