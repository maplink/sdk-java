package global.maplink.geocode.async;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeocodeAsyncApiTest {

    @Test
    public void mustBeInstantiableWithGetInstance() {
        GeocodeAsyncAPI instance = GeocodeAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }
}
