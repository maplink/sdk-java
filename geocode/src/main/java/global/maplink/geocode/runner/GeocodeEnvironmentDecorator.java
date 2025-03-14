package global.maplink.geocode.runner;

import global.maplink.env.Environment;
import global.maplink.geocode.GeocodeVersion;
import lombok.RequiredArgsConstructor;

import java.net.URL;

@RequiredArgsConstructor
public class GeocodeEnvironmentDecorator implements Environment {

    private final Environment delegate;

    private final GeocodeVersion version;

    @Override
    public String getHost() {
        return delegate.getHost();
    }

    @Override
    public URL withService(String servicePath) {
        return delegate.withService(version.apply(servicePath));
    }
}
