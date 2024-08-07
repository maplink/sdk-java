package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.freight.schema.OperationType.C;
import static global.maplink.planning.schema.problem.OperationStatus.FREE;
import static global.maplink.planning.testUtils.ProblemSampleFiles.OPERATION;
import static org.assertj.core.api.Assertions.assertThat;

class OperationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Operation operation = mapper.fromJson(OPERATION.load(), Operation.class);

        assertThat(operation.getId()).isEqualTo("exemplo1");
        assertThat(operation.getGroup()).isEqualTo("exemplo2");
        assertThat(operation.getProduct()).isEqualTo("exemplo3");
        assertThat(operation.getType()).isEqualTo(C);
        assertThat(operation.getDepotSize()).isEqualTo("exemplo5");
        assertThat(operation.getCustomerSite()).isEqualTo("exemplo6");
        assertThat(operation.getPreAllocatedVehicleName()).isEqualTo("exemplo7");
        assertThat(operation.getStatus()).isEqualTo(FREE);
        assertThat(operation.getWeight()).isEqualTo(10.0);
        assertThat(operation.getVolume()).isEqualTo(11.0);
        assertThat(operation.getQuantity()).isEqualTo(12.0);
        assertThat(operation.getPriority()).isEqualTo(13);
        assertThat(operation.getDepotHandlingDuration()).isEqualTo(14);
        assertThat(operation.getCustomerTimeWindowBlocked()).isFalse();
        assertThat(operation.getDepotTimeWindowBlocked()).isTrue();

        assertThat(operation.getCharacteristics()).hasSize(2);
        assertThat(operation.getCharacteristics()).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(operation.getCustomerTimeWindows()).hasSize(1);
        assertThat(operation.getCustomerTimeWindows().get(0).getStart()).isEqualTo(123);
        assertThat(operation.getCustomerTimeWindows().get(0).getEnd()).isEqualTo(456);
        assertThat(operation.getDepotTimeWindows()).hasSize(1);
        assertThat(operation.getDepotTimeWindows().get(0).getStart()).isEqualTo(111);
        assertThat(operation.getDepotTimeWindows().get(0).getEnd()).isEqualTo(222);
    }
}

