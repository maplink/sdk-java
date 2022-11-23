package global.maplink.tracking.schema.schema.domain;

import global.maplink.tracking.schema.schema.exceptions.ErrorType;
import global.maplink.tracking.schema.schema.exceptions.TrackingException;
import lombok.*;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Theme {

    public static final String patternRegex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private final String id;
    private final String logo;
    private final String color;
    private final String icon;
    private final Locale language;
    private final Audit audit;


    public void validate() {
        if (color == null) throw new TrackingException(ErrorType.TRACKING_01);

        Pattern p = Pattern.compile(patternRegex);
        Matcher m = p.matcher(color);

        if (!m.matches()) throw new TrackingException(ErrorType.TRACKING_02);
    }

}