package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.COMPARTMENT;
import static org.assertj.core.api.Assertions.assertThat;

class CompartmentTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Compartment compartment = mapper.fromJson(COMPARTMENT.load(), Compartment.class);

        assertThat(compartment.getName()).isEqualTo("exemplo1");
        assertThat(compartment.getMinimumCapacity()).isEqualTo(10.0);
        assertThat(compartment.getMaximumCapacity()).isEqualTo(20.0);
        assertThat(compartment.getIncrement()).isEqualTo(30.0);
        assertThat(compartment.getLoadingRule()).isEqualTo("exemplo2");
        assertThat(compartment.getAllowedPackagings()).hasSize(2);
    }
}
