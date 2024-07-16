package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.schema.problem.CompartmentLoadingRule.NONE;
import static global.maplink.planning.testUtils.ProblemSampleFiles.COMPARTMENT_CONFIGURATION;
import static org.assertj.core.api.Assertions.assertThat;

class CompartmentConfigurationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        CompartmentConfiguration compartmentConfiguration = mapper.fromJson(COMPARTMENT_CONFIGURATION.load(), CompartmentConfiguration.class);

        assertThat(compartmentConfiguration.getName()).isEqualTo("exemplo1");
        assertThat(compartmentConfiguration.getCompartments()).hasSize(1);
        assertThat(compartmentConfiguration.getCompartments().get(0).getName()).isEqualTo("exemplo1");
        assertThat(compartmentConfiguration.getCompartments().get(0).getMinimumCapacity()).isEqualTo(10.0);
        assertThat(compartmentConfiguration.getCompartments().get(0).getMaximumCapacity()).isEqualTo(20.0);
        assertThat(compartmentConfiguration.getCompartments().get(0).getIncrement()).isEqualTo(30.0);
        assertThat(compartmentConfiguration.getCompartments().get(0).getLoadingRule()).isEqualTo(NONE);
    }
}
