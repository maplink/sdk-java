package global.maplink.credentials;

public class NoCredentialsProvidedException extends RuntimeException {

    public NoCredentialsProvidedException() {
        super("MapLink Token must be provided");
    }
}
