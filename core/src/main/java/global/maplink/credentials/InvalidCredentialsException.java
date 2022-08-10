package global.maplink.credentials;

import static java.lang.String.format;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String clientId, String failMessage) {
        super(format("Fail to fetchToken for [clientId: %s]: %s", clientId, failMessage));
    }
}
