package global.maplink.extensions;

public interface SdkExtension extends Comparable<SdkExtension> {

    int DEFAULT_PRIORITY = 1000;

    String getName();

    default int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    default int compareTo(SdkExtension o) {
        if (o == null) {
            return 1;
        }
        return Integer.compare(getPriority(), o.getPriority());
    }
}
