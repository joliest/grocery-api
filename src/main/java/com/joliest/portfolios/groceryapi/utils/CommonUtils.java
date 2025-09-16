package com.joliest.portfolios.groceryapi.utils;

import java.util.Optional;

public class CommonUtils {
    public static <T> T defaultIfNull(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
}
