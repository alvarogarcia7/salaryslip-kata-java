package com.example.kata.salaryslip;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

public class Year2017IncomeTaxCalculator implements IncomeTaxCalculator {
    @Override
    public BigDecimal taxableIncomeFor (final Employee employee) {
        final BigDecimal grossAnnualSalary = employee.grossAnnualSalary();
        final BigDecimal personalAllowanceThreshold = valueOf(100_000);

        BigDecimal personalAllowance = valueOf(11000);
        personalAllowance = reducePersonalAllowance(personalAllowance, grossAnnualSalary, personalAllowanceThreshold);
        return getNumberOrZero(grossAnnualSalary.subtract(personalAllowance));
    }

    private BigDecimal reducePersonalAllowance (BigDecimal personalAllowance, final BigDecimal grossAnnualSalary, final BigDecimal personalAllowanceThreshold) {
        if(firstIsGreaterThan(grossAnnualSalary, personalAllowanceThreshold)) {
            personalAllowance = personalAllowance.subtract(grossAnnualSalary.subtract(personalAllowanceThreshold).divide(valueOf(2)));
            personalAllowance = getNumberOrZero(personalAllowance);
        }
        return personalAllowance;
    }

    private BigDecimal getNumberOrZero (BigDecimal personalAllowance) {
        if (firstIsGreaterThan(BigDecimal.ZERO, personalAllowance)) {
            personalAllowance = BigDecimal.ZERO;
        }
        return personalAllowance;
    }

    private static boolean firstIsGreaterThan (final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) == 1;
    }
}
