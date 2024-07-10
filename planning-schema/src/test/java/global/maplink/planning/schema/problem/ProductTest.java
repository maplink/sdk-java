package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void shouldValidate() {
        val product = Product.builder().build();
        assertThat(product.validate()).isNotEmpty().hasSize(4);

        assertThatThrownBy(product::throwIfInvalid).isInstanceOf(ValidationException.class);
    }
}

