package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class Year2017IncomeTaxCalculator implements IncomeTaxCalculator {
    @Override
    public BigDecimal taxableIncomeFor (final Employee employee) {
        BigDecimal personalAllowance = BigDecimal.valueOf(11000);
        final BigDecimal grossAnnualSalary = employee.grossAnnualSalary();
        final BigDecimal personalAllowanceThreshold = BigDecimal.valueOf(100_000);
        if(firstIsGreaterThan(grossAnnualSalary, personalAllowanceThreshold)) {
            personalAllowance = personalAllowance.subtract(grossAnnualSalary.subtract(personalAllowanceThreshold).divide(BigDecimal
                    .valueOf(2)));
        }
        if (firstIsGreaterThan(grossAnnualSalary, personalAllowance)) {
            return grossAnnualSalary.subtract(personalAllowance);
        }
        return BigDecimal.ZERO;
    }

    private boolean firstIsGreaterThan (final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) == 1;
    }
}
