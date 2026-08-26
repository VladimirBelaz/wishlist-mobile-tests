package ru.otus.factory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.appium.java_client.android.AndroidDriver;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import ru.otus.config.TestConfig;
import ru.otus.emulator.Emulator;
import ru.otus.emulator.EmulatorProvider;

import java.net.URI;
import java.time.Duration;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class AndroidDriverFactory {

    private final EmulatorProvider emulatorProvider;
    private final Capabilities capabilities;
    private final TestConfig config;

    @SneakyThrows
    public WebDriver create() {
        Emulator emulator = emulatorProvider.takeAndGet();
        AndroidDriver driver =
                new AndroidDriver(
                        URI.create(
                                "http://%s:%d/".formatted(
                                        config.getAppiumHost(),
                                        emulator.getPort()
                                )
                        ).toURL(),
                        capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}