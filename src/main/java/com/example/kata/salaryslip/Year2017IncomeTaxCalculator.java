package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class Year2017IncomeTaxCalculator implements IncomeTaxCalculator {
    @Override
    public BigDecimal taxableIncomeFor (final Employee employee) {
        final BigDecimal personalAllowance = BigDecimal.valueOf(11000);
        if (firstIsGreaterThan(employee.grossAnnualSalary(), personalAllowance)) {
            return employee.grossAnnualSalary().subtract(personalAllowance);
        }
        return BigDecimal.ZERO;
    }

    private boolean firstIsGreaterThan (final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) == 1;
    }
}
