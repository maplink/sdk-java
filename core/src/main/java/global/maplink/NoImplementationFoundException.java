package global.maplink;

import lombok.Getter;

@Getter
public class NoImplementationFoundException extends RuntimeException {

    private final Class<?> target;

    public NoImplementationFoundException(Class<?> target) {
        super(String.format("No implementation found for [type: %s] in [classLoader: %s]", target.getName(), target.getClassLoader()));
        this.target = target;
    }
}
