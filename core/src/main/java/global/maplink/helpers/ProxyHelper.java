package global.maplink.helpers;

import lombok.SneakyThrows;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class ProxyHelper {
    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

    @SneakyThrows
    public static MethodHandle handleFor(Method method) {
        MethodType methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
        return lookup.findSpecial(method.getDeclaringClass(), method.getName(), methodType, method.getDeclaringClass());
    }

}
