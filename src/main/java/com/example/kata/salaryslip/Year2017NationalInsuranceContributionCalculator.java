package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class Year2017NationalInsuranceContributionCalculator implements NationalInsuranceContributionCalculator {
    @Override
    public BigDecimal amountFor (final Employee employee) {
        final BigDecimal annualGrossSalary = employee.grossAnnualSalary();
        final BigDecimal basicContributionsTreshold = BigDecimal.valueOf(8060.00);
        if (firstIsGreaterThanSecond(annualGrossSalary, BigDecimal.valueOf(43000.00))) {

            final BigDecimal higherContributions = annualGrossSalary.subtract(BigDecimal.valueOf(43000.00));
            final BigDecimal higher = higherContributions.multiply(BigDecimal.valueOf(0.02));

            final BigDecimal basic = BigDecimal.valueOf(43000).subtract(BigDecimal.valueOf(8060)).multiply(BigDecimal.valueOf(0.12));
            return higher.add(basic);
        }
        if (firstIsGreaterThanSecond(annualGrossSalary, basicContributionsTreshold)) {
            final BigDecimal basicContributions = annualGrossSalary.subtract(basicContributionsTreshold);
            return basicContributions.multiply(BigDecimal.valueOf(0.12));
        }

        return BigDecimal.ZERO;
    }

    private boolean firstIsGreaterThanSecond (final BigDecimal annualGrossSalary, final BigDecimal val) {
        return annualGrossSalary.compareTo(val) == 1;
    }
}
