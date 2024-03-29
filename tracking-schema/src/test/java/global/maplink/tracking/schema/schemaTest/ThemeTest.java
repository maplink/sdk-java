package global.maplink.tracking.schema.schemaTest;

import global.maplink.json.JsonMapper;
import global.maplink.tracking.schema.domain.Audit;
import global.maplink.tracking.schema.domain.Theme;
import lombok.var;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Locale;

import static global.maplink.tracking.schema.errors.ValidationErrorType.*;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_THEME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    public void isValidHexadecimalCorrect() {
        Theme theme = Theme.builder()
                .id("default")
                .logo("cutomize Theme")
                .color("#1AFFa1")
                .language(new Locale("pt", "BR"))
                .audit(Audit.builder()
                        .createdAt(Instant.parse("2022-11-28T16:00:00.120Z"))
                        .updatedAt(Instant.parse("2022-11-28T16:30:00.120Z"))
                        .build())
                .build();

        assertDoesNotThrow(theme::validate);
    }

    @Test
    public void isValidHexadecimalIncorrect() {
        Theme theme = Theme.builder()
                .id("default")
                .logo("cutomize Theme")
                .color("1AFFa1####")
                .language(new Locale("pt", "BR"))
                .audit(Audit.builder()
                        .createdAt(Instant.parse("2022-11-28T16:00:00.120Z"))
                        .updatedAt(Instant.parse("2022-11-28T16:30:00.120Z"))
                        .build())
                .build();

        var errors = theme.validate();
        assertEquals(THEME_COLOR_INCORRECT, errors.get(0));
    }

    @Test
    public void isValidateColorNull() {
        Theme theme = Theme.builder()
                .id("default")
                .logo("cutomize Theme")
                .color(null)
                .language(new Locale("pt", "BR"))
                .audit(Audit.builder()
                        .createdAt(Instant.parse("2022-11-28T16:00:00.120Z"))
                        .updatedAt(Instant.parse("2022-11-28T16:30:00.120Z"))
                        .build())
                .build();

        var errors = theme.validate();
        assertEquals(THEME_COLOR_NOTNULL, errors.get(0));
    }

    @Test
    public void isValidateIdNull() {
        Theme theme = Theme.builder()
                .id(null)
                .logo("cutomize Theme")
                .color("#1AFFa1")
                .language(new Locale("pt", "BR"))
                .audit(Audit.builder()
                        .createdAt(Instant.parse("2022-11-28T16:00:00.120Z"))
                        .updatedAt(Instant.parse("2022-11-28T16:30:00.120Z"))
                        .build())
                .build();

        var errors = theme.validate();
        assertEquals(THEME_ID_NOTNULL, errors.get(0));
    }

    @Test
    public void isValidateLanguageNull() {
        Theme theme = Theme.builder()
                .id("default")
                .logo("cutomize Theme")
                .color("#1AFFa1")
                .language(null)
                .audit(Audit.builder()
                        .createdAt(Instant.parse("2022-11-28T16:00:00.120Z"))
                        .updatedAt(Instant.parse("2022-11-28T16:30:00.120Z"))
                        .build())
                .build();

        var errors = theme.validate();
        assertEquals(THEME_LANGUAGE_NOTNULL, errors.get(0));
    }
}