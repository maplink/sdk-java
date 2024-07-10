package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Product product = mapper.fromJson(PRODUCT.load(), Product.class);

        assertThat(product.getName()).isEqualTo("exemplo1");
        assertThat(product.getType()).isEqualTo("exemplo2");
        assertThat(product.getIncompabilityGroup()).isEqualTo("exemplo3");
        assertThat(product.getPackagings()).hasSize(2);
        assertThat(product.getPackagings()).containsExactlyInAnyOrder("ex1", "ex2");
    }
}

