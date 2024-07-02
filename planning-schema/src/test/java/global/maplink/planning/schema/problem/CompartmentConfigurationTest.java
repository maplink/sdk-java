package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.COMPARTMENT_CONFIGURATION;
import static org.assertj.core.api.Assertions.assertThat;

public class CompartmentConfigurationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        CompartmentConfiguration compartmentConfiguration = mapper.fromJson(COMPARTMENT_CONFIGURATION.load(), CompartmentConfiguration.class);

        assertThat(compartmentConfiguration.getName()).isEqualTo("exemplo1");
        assertThat(compartmentConfiguration.getCompartments()).hasSize(2);
        assertThat(compartmentConfiguration.getCompartments().get(0)).isEqualTo("exemplo1");
        assertThat(compartmentConfiguration.getCompartments().get(1)).isEqualTo("exemplo2");
    }
}
