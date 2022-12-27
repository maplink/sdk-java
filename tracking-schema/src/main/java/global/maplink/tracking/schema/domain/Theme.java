package global.maplink.tracking.schema.domain;

import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static global.maplink.tracking.schema.errors.ValidationErrorType.*;
import static java.util.Objects.isNull;

@Data
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Theme implements Validable {

    public static final String COLOR_HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private final String id;
    private final String logo;
    private final String color;
    private final String favicon;
    private final Locale language;
    private final Audit audit;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new ArrayList<>();
        if (isInvalid(id)) {
            violations.add(THEME_ID_NOTNULL);
        }
        if (isNull(language)) {
            violations.add(THEME_LANGUAGE_NOTNULL);
        }
        if (isInvalid(color)) {
            violations.add(THEME_COLOR_NOTNULL);
        } else {
            Pattern p = Pattern.compile(COLOR_HEX_PATTERN);
            Matcher m = p.matcher(color);

            if (!m.matches()) violations.add(THEME_COLOR_INCORRECT);
        }
        return violations;
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }

}