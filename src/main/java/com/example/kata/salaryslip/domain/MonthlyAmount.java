package com.example.kata.salaryslip.domain;

import java.math.BigDecimal;

public class MonthlyAmount {
    private final BigDecimal value;

    public static MonthlyAmount valueOf (final BigDecimal value) {
        return new MonthlyAmount(value);
    }

    private MonthlyAmount (final BigDecimal value) {
        this.value = value;
    }

    public BigDecimal value () {
        return value;
    }

    @Override
    public boolean equals (final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MonthlyAmount that = (MonthlyAmount) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode () {
        return value.hashCode();
    }
}
