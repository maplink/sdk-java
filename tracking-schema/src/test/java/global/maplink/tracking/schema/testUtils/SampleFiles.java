package global.maplink.tracking.schema.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum SampleFiles {

    TRACKING_ORDER("jsonTracking/order.json"),
    TRACKING_ORDER_WITH_NULL_DESCRIPTION("jsonTracking/orderWithNullDescription.json"),
    TRACKING_ORDER_WITH_BLANK_DESCRIPTION("jsonTracking/orderWithBlankDescription.json"),
    TRACKING_ORDER_WITH_NULL_DESTINATION("jsonTracking/orderWithNullDestination.json"),
    TRACKING_ORDER_WITH_NULL_DESTINATION_MAIN_LOCATION("jsonTracking/orderWithNullDestinationMainLocation.json"),
    TRACKING_ORDER_WITH_DESTINATION_LATLON_NULL("jsonTracking/orderWithDestinationLatLonNull.json"),
    TRACKING_ORDER_WITH_NULL_DRIVER_CURRENT_LOCATION("jsonTracking/orderWithNullDriverCurrentLocation.json"),
    TRACKING_ORDER_WITH_DRIVER_LATLON_NULL("jsonTracking/orderWithDriverLatLonNull.json"),
    TRACKING_ORDER_WITH_NULL_STATUS_VALUE("jsonTracking/orderWithNullStatusValue.json"),
    TRACKING_ORDER_WITH_NULL_STATUS_LABEL("jsonTracking/orderWithNullStatusLabel.json"),
    TRACKING_ORDER_WITH_ORIGIN_LATLON_NULL("jsonTracking/orderWithOriginLatLonNull.json"),

    TRACKING_THEME("jsonTracking/theme.json"),
    TRACKING_THEME_WITH_NULL_ID("jsonTracking/themeWithNullId.json"),
    TRACKING_THEME_WITH_BLANK_ID("jsonTracking/themeWithBlankId.json"),
    TRACKING_THEME_WITH_NULL_LANGUAGE("jsonTracking/themeWithNullLanguage.json"),
    TRACKING_THEME_WITH_NULL_COLOR("jsonTracking/themeWithNullColor.json"),
    TRACKING_THEME_WITH_INVALID_COLOR("jsonTracking/themeWithInvalidColor.json"),
    TRACKING_THEME_WITH_SHORT_HEX_COLOR("jsonTracking/themeWithShortHexColor.json");

    private final String filePath;

    public byte[] load() {
        URL resource = SampleFiles.class.getClassLoader().getResource(filePath);
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
