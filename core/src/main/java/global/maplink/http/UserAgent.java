package global.maplink.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Getter
public class UserAgent {

    private static final String ENV_NAME = "MAPLINK_USER_AGENT";
    private static final String PROP_NAME = "maplink.sdk.userAgent";
    private static final String POM_PROPERTIES = "/META-INF/maven/global.maplink/sdk-core/pom.properties";

    private final String agentDetails;

    private final String javaVersion;

    private final String sdkVersion;

    private final String operationalSystem;

    private String headerContent = null;

    public String getHeaderContent() {
        if (headerContent == null) {
            headerContent = Stream.of(
                    "Maplink SDK",
                    sdkVersion,
                    "Java",
                    javaVersion,
                    operationalSystem,
                    agentDetails
            ).filter(Objects::nonNull).collect(joining(" "));
        }
        return headerContent;
    }

    public static UserAgent loadDefault(String agentDetails) {
        return new UserAgent(
                agentDetails,
                fetchJavaVersion(),
                fetchSdkVersion(),
                fetchOS()
        );
    }

    public static UserAgent loadDefault() {
        String envAgent = getenv(ENV_NAME);
        String propertyAgent = getProperty(PROP_NAME, null);
        return loadDefault(Optional.ofNullable(envAgent).orElse(propertyAgent));
    }

    private static String fetchOS() {
        return format(
                "%s %s (%s)",
                getProperty("os.name"),
                getProperty("os.arch"),
                getProperty("os.version")
        );
    }

    private static String fetchSdkVersion() {
        try (InputStream is = UserAgent.class.getResourceAsStream(POM_PROPERTIES)) {
            Properties props = new Properties();
            props.load(is);
            return props.getProperty("version");
        } catch (Exception e) {
            return null;
        }
    }

    private static String fetchJavaVersion() {
        return format(
                "%s (%s)",
                getProperty("java.version"),
                getProperty("java.vendor")
        );
    }

}
