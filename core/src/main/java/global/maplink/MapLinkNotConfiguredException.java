package global.maplink;

public class MapLinkNotConfiguredException extends RuntimeException {
    public MapLinkNotConfiguredException() {
        super("MapLink.configure must be invoked before usage");
    }
}
