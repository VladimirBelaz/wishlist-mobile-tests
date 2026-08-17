package ru.otus.emulator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Emulator {

    ANDROID_12(4723),
    ANDROID_14(4724);

    private final int port;
}