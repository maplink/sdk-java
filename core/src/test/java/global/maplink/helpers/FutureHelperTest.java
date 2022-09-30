package global.maplink.helpers;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static global.maplink.helpers.FutureHelper.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FutureHelperTest {

    @Test
    public void mustAwaitForExecution() {
        val future = CompletableFuture.supplyAsync(() -> {
            sleep(10);
            return true;
        });
        val result = await(future);
        assertThat(result).isTrue();
    }

    @Test
    public void mustAwaitAndRethrowRuntimeExceptions() {
        val future = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            throw new SampleUncheckedException();
        });
        assertThatThrownBy(() -> await(future)).isInstanceOf(SampleUncheckedException.class);
    }

    @SneakyThrows
    private void sleep(int time) {
        Thread.sleep(time);
    }

    static class SampleUncheckedException extends RuntimeException {
    }

}
