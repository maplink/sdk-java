package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.PRODUCT2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductValidatorTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void violationsAreCorrect(){

        Product product = mapper.fromJson(PRODUCT2.load(), Product.class);

        assertThat(product.validate().get(0).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: product.name)");
        assertThat(product.validate().get(1).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: product.type)");
        assertThat(product.validate().get(2).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: product.incompabilityGroup)");
        assertThat(product.validate().get(3).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: product.packagings)");
    }

    @Test
    void shouldValidate() {
        val product = Product.builder().build();
        assertThat(product.validate()).isNotEmpty().hasSize(4);

        assertThatThrownBy(product::throwIfInvalid).isInstanceOf(ValidationException.class);
    }
}

