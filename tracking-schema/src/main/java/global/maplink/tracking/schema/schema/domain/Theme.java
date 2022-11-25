package global.maplink.tracking.schema.schema.domain;

import global.maplink.tracking.schema.schema.exceptions.TrackingValidationException;
import lombok.*;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static global.maplink.tracking.schema.schema.exceptions.ValidationErrorType.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Theme {

    public static final String COLOR_HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private final String id;
    private final String logo;
    private final String color;
    private final String icon;
    private final Locale language;
    private final Audit audit;

    public void validate() {
        if (isInvalid(id)) {
            throw new TrackingValidationException(THEME_ID_NOTNULL);
        }
        if (isInvalid(language)) {
            throw new TrackingValidationException(THEME_LANGUAGE_NOTNULL);
        }
        if (isInvalid(color)) {
            throw new TrackingValidationException(THEME_COLOR_NOTNULL);
        } else {
            Pattern p = Pattern.compile(COLOR_HEX_PATTERN);
            Matcher m = p.matcher(color);

            if (!m.matches()) throw new TrackingValidationException(THEME_COLOR_INCORRECT);
        }
    }

    private boolean isInvalid(final String value) {
        return Objects.isNull(value) || value.trim().isEmpty();
    }

    private boolean isInvalid(final Locale value) {
        return Objects.isNull(value);
    }

}