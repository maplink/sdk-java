package global.maplink.trip.schema.v2.features.turnByTurn;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Languages {

    PT_BR("pt_BR"),
    ES_AR("es_AR"),
    EN("en");

    private final String languageCode;

    @JsonValue
    public String getLanguageCode() {
        return languageCode;
    }
}