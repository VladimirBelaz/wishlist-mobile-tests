package ru.otus.extension;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.otus.factory.AndroidDriverModule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class InjectorService {

    private static final Injector INJECTOR = Guice.createInjector(new AndroidDriverModule());

    public static void injectMembers(Object instance) {
        INJECTOR.injectMembers(instance);
    }

    public static <T> T getInstance(Class<T> clazz) {
        return INJECTOR.getInstance(clazz);
    }
}