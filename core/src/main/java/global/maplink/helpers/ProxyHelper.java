package global.maplink.helpers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.synchronizedMap;

@Slf4j
public class ProxyHelper {
    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final Map<Method, MethodHandle> cache = synchronizedMap(new WeakHashMap<>());

    private static final List<Handler> handlers = Arrays.asList(
            ProxyHelper::safeHandler,
            ProxyHelper::unsafeHandler
    );

    @SneakyThrows
    public static MethodHandle handleFor(Method method) {
        if (cache.containsKey(method)) {
            return cache.get(method);
        }
        MethodHandle result = getFromHandlers(method);
        cache.put(method, result);
        return result;
    }

    private static MethodHandle getFromHandlers(Method method) {
        List<Exception> errors = new LinkedList<>();
        for (Handler h : handlers) {
            try {
                return h.handleFor(method);
            } catch (Exception e) {
                errors.add(e);
            }
        }
        Exception last = null;
        for (Exception e : errors) {
            log.warn("Error on method handler creation", e);
            last = e;
        }
        throw new IllegalArgumentException(format(
                "Cannot create handler for %s %s.%s(%s)",
                method.getReturnType(),
                method.getDeclaringClass(),
                method.getName(),
                Arrays.toString(method.getParameterTypes())
        ), last);
    }

    private static MethodHandle safeHandler(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
        return lookup.findSpecial(method.getDeclaringClass(), method.getName(), methodType, method.getDeclaringClass());
    }

    private static MethodHandle unsafeHandler(Method method) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
        constructor.setAccessible(true);
        return constructor.newInstance(method.getDeclaringClass())
                .in(method.getDeclaringClass())
                .unreflectSpecial(method, method.getDeclaringClass());
    }

    @FunctionalInterface
    private interface Handler {
        MethodHandle handleFor(Method method) throws Exception;
    }

}
