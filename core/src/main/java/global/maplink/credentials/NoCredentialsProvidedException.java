package global.maplink.credentials;

public class NoCredentialsProvidedException extends RuntimeException {

    public NoCredentialsProvidedException() {
        super("MapLink API key and secret must be provided");
    }
}
