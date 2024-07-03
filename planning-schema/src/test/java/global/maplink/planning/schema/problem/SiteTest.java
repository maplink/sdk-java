package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.SITE;
import static org.assertj.core.api.Assertions.assertThat;

class SiteTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Site site = mapper.fromJson(SITE.load(), Site.class);

        assertThat(site.getSite()).isEqualTo("exemplo1");
        assertThat(site.getLogisticConstraints()).isEqualTo("exemplo2");
        assertThat(site.getLogisticZones()).hasSize(2);
        assertThat(site.getLogisticZones()).containsExactlyInAnyOrder("ex1", "ex2");
    }
}

