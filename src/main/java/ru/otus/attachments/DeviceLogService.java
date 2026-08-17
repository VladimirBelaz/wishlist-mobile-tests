package ru.otus.attachments;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import ru.otus.utils.FileUtils;

import java.nio.file.Files;
import java.util.List;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class DeviceLogService {

    private final FileUtils fileUtils;

    @SneakyThrows
    public void attach(WebDriver driver) {
        List<String> logs =
                driver.manage().logs().get("logcat").getAll().stream().map(Object::toString).toList();
        String filename = "AndroidLogs%d.log".formatted(System.currentTimeMillis());
        Files.write(fileUtils.getLogPath(filename), logs);
    }
}
