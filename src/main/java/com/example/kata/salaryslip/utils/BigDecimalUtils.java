package com.example.kata.salaryslip.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static boolean firstIsGreaterThan (final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) == 1;
    }
}
