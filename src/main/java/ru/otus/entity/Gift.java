package ru.otus.entity;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Gift {
    String name;
    BigDecimal price;
    String description;
}