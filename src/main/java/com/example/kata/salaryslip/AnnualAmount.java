package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class AnnualAmount {
    private final BigDecimal value;

    private AnnualAmount (final BigDecimal value) {
        this.value = value;
    }

    public MonthlyAmount toMonthly () {
        return MonthlyAmount.valueOf(value().divide(BigDecimal.valueOf(12)));
    }

    public static AnnualAmount valueOf (final BigDecimal value) {
        return new AnnualAmount(value);
    }

    @Override
    public boolean equals (final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AnnualAmount that = (AnnualAmount) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode () {
        return value.hashCode();
    }

    public BigDecimal value () {
        return value.setScale(2);
    }

    @Override
    public String toString () {
        return value.toString();
    }

    public boolean isEqual (final AnnualAmount expected) {
        return this.value.compareTo(expected.value) == 0;
    }
}
