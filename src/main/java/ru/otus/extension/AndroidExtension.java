package ru.otus.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.openqa.selenium.WebDriver;
import ru.otus.attachments.DeviceLogService;
import ru.otus.emulator.EmulatorProvider;
import ru.otus.factory.AndroidDriverFactory;

@NullMarked
public class AndroidExtension
        implements TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback, AfterTestExecutionCallback {

    private final AndroidDriverFactory factory =
            InjectorService.getInstance(AndroidDriverFactory.class);
    private final DeviceLogService deviceLogService =
            InjectorService.getInstance(DeviceLogService.class);
    private final EmulatorProvider emulatorProvider =
            InjectorService.getInstance(EmulatorProvider.class);

    @Override
    public void afterEach(ExtensionContext context) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        if (driver != null) {
            // Возвращаем эмулятор в пул и закрываем драйвер
            emulatorProvider.putBack();
            driver.quit();
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        WebDriver driver = InjectorService.getInstance(WebDriver.class);
        WebDriverRunner.setWebDriver(driver);
        Selenide.open();
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        InjectorService.injectMembers(testInstance);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        if (driver != null) {
            deviceLogService.attach(driver);
        }
    }
}