package global.maplink.http.exceptions;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class MapLinkHttpExceptionTest {

    @Test
    void mustGenerateMessageFromJson() {
        MapLinkHttpException jsonFail = new MapLinkHttpException(400, "application/Json; encoding=utf-8", "{}".getBytes(StandardCharsets.UTF_8));
        assertThat(jsonFail.getMessage()).contains(
                "application/Json",
                "400",
                "{}"
        );
    }

    @Test
    void mustGenerateMessageFromText() {
        MapLinkHttpException textFail = new MapLinkHttpException(400, "text/plain", "Erro em texto".getBytes(StandardCharsets.UTF_8));
        assertThat(textFail.getMessage()).contains(
                "text/plain",
                "400",
                "Erro em texto"
        );
    }

    @Test
    void mustGenerateMessageFromByteArray() {
        MapLinkHttpException binaryFail = new MapLinkHttpException(400, "image/png", "Isto é uma imagem".getBytes(StandardCharsets.UTF_8));
        assertThat(binaryFail.getMessage()).contains(
                "image/png",
                "400",
                "18 bytes"
        );
    }
}