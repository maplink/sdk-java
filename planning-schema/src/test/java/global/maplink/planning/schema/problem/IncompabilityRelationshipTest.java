package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.INCOMPABILITY_RELATIONSHIP;
import static org.assertj.core.api.Assertions.assertThat;

class IncompabilityRelationshipTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        IncompabilityRelationship incompabilityRelationship = mapper.fromJson(INCOMPABILITY_RELATIONSHIP.load(), IncompabilityRelationship.class);

        assertThat(incompabilityRelationship.getName()).isEqualTo("exemplo1");
        assertThat(incompabilityRelationship.getIncompabilityGroup1()).isEqualTo("exemplo2");
        assertThat(incompabilityRelationship.getIncompabilityGroup2()).isEqualTo("exemplo3");
        assertThat(incompabilityRelationship.getVehicles().size()).isEqualTo(3);
        assertThat(incompabilityRelationship.getVehicles().get(0)).isEqualTo("vehicle1");
        assertThat(incompabilityRelationship.getVehicles().get(1)).isEqualTo("vehicle2");
        assertThat(incompabilityRelationship.getVehicles().get(2)).isEqualTo("vehicle3");
    }
}
