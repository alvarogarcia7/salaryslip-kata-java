package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class BigDecimalUtils {
    static boolean firstIsGreaterThan (final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) == 1;
    }
}
