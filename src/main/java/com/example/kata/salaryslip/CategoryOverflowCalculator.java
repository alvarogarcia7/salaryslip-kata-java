package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.List;

import static com.example.kata.salaryslip.BigDecimalUtils.firstIsGreaterThan;
import static java.math.BigDecimal.ZERO;

public class CategoryOverflowCalculator {
    private final List<TaxBand> taxBands;

    public CategoryOverflowCalculator (final List<TaxBand> taxBands) {
        this.taxBands = taxBands;
    }

    public BigDecimal forAmount (final BigDecimal grossAnnualSalary) {
        BigDecimal remaining = grossAnnualSalary;
        BigDecimal accumulatedContribution = ZERO;
        for (TaxBand taxBand : taxBands) {
            if (firstIsGreaterThan(remaining, taxBand.lowerBound())) {
                final BigDecimal amountInThisBand = remaining.subtract(taxBand.lowerBound());
                final BigDecimal contributionInThisBand = amountInThisBand.multiply(taxBand.taxRate());
                accumulatedContribution = accumulatedContribution.add(contributionInThisBand);
                remaining = remaining.subtract(amountInThisBand);
            }
        }
        return accumulatedContribution;
    }
}
