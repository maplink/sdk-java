package global.maplink.planning.schema.solution;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.PENDING_TASKS;
import static org.assertj.core.api.Assertions.assertThat;


class PendingTasksTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        PendingTasks pendingTasks = mapper.fromJson(PENDING_TASKS.load(), PendingTasks.class);

        assertThat(pendingTasks.getTrip()).hasSize(2);
        assertThat(pendingTasks.getTrip()).containsExactlyInAnyOrder("ex1", "ex2");
    }
}