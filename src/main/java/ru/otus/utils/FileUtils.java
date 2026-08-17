package ru.otus.utils;

import com.google.inject.Singleton;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
public class FileUtils {

    @SneakyThrows
    public Path getLogPath(String filename) {
        return Files.createDirectories(Paths.get("logs")).resolve(filename);
    }

}
