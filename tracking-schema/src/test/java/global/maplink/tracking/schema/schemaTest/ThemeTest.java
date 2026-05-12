package global.maplink.tracking.schema.schemaTest;

import global.maplink.json.JsonMapper;
import global.maplink.tracking.schema.domain.Theme;
import global.maplink.tracking.schema.errors.TrackingViolation;
import global.maplink.tracking.schema.testUtils.SampleFiles;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import lombok.var;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME_WITH_BLANK_ID;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME_WITH_INVALID_COLOR;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME_WITH_NULL_COLOR;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME_WITH_NULL_ID;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME_WITH_NULL_LANGUAGE;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME_WITH_SHORT_HEX_COLOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThemeTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToThemeRequestTest() {
        Theme theme = mapper.fromJson(TRACKING_THEME.load(), Theme.class);
        var language = new Locale("pt", "BR");

        assertEquals("DEFAULT", theme.getId());
        assertEquals("https://example.logo.com", theme.getLogo());
        assertEquals("#FF0000", theme.getColor());
        assertEquals("https://example.icon.com", theme.getFavicon());
        assertEquals(language, theme.getLanguage());
        assertEquals(Instant.parse("2022-11-28T16:00:00.120Z"), theme.getAudit().getCreatedAt());
        assertEquals(Instant.parse("2022-11-28T16:30:00.120Z"), theme.getAudit().getUpdatedAt());
    }

    @Test
    void shouldValidate() {
        Theme theme = mapper.fromJson(TRACKING_THEME.load(), Theme.class);
        assertThat(theme.validate()).isEmpty();
    }

    @Test
    void shouldAcceptShortHexadecimalColor() {
        Theme theme = mapper.fromJson(TRACKING_THEME_WITH_SHORT_HEX_COLOR.load(), Theme.class);
        assertThat(theme.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidThemeProvider")
    void shouldRejectInvalidTheme(SampleFiles sampleFile, String expectedMessage) {
        Theme theme = mapper.fromJson(sampleFile.load(), Theme.class);
        List<ValidationViolation> violations = theme.validate();

        assertThatThrownBy(theme::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TrackingViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("invalidThemeProvider")
    void shouldSerializeViolationMessageCorrectly(SampleFiles sampleFile, String expectedMessage) {
        Theme theme = mapper.fromJson(sampleFile.load(), Theme.class);
        String serialized = mapper.toJsonString(theme.validate());
        assertThat(serialized).contains("\"message\":\"" + expectedMessage + "\"");
    }

    static Stream<Arguments> invalidThemeProvider() {
        return Stream.of(
                Arguments.of(TRACKING_THEME_WITH_NULL_ID,         "id cannot be null"),
                Arguments.of(TRACKING_THEME_WITH_BLANK_ID,        "id cannot be null"),
                Arguments.of(TRACKING_THEME_WITH_NULL_LANGUAGE,   "language cannot be null"),
                Arguments.of(TRACKING_THEME_WITH_NULL_COLOR,      "color cannot be null"),
                Arguments.of(TRACKING_THEME_WITH_INVALID_COLOR,   "color must be a valid hexadecimal value, like #000000")
        );
    }
}
