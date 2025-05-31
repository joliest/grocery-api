package com.joliest.portfolios.groceryapi.testHelper;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class MockHelpers {
    public static <T> Slice<T> mockSlice(List<T> items) {
        return new SliceImpl<>(items);
    }
}
