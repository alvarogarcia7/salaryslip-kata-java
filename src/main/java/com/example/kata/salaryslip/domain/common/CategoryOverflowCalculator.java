package com.example.kata.salaryslip.domain.common;

import com.example.kata.salaryslip.domain.AnnualAmount;
import com.example.kata.salaryslip.domain.TaxBand;

import java.math.BigDecimal;
import java.util.List;

import static com.example.kata.salaryslip.utils.BigDecimalUtils.firstIsGreaterThan;
import static java.math.BigDecimal.ZERO;

public class CategoryOverflowCalculator {
    private final List<TaxBand> taxBands;

    public CategoryOverflowCalculator (final List<TaxBand> taxBands) {
        this.taxBands = taxBands;
    }

    public AnnualAmount forAmount (final AnnualAmount grossAnnualSalary) {
        BigDecimal remaining = grossAnnualSalary.value();
        BigDecimal accumulatedContribution = ZERO;
        for (TaxBand taxBand : taxBands) {
            if (firstIsGreaterThan(remaining, taxBand.lowerBound())) {
                final BigDecimal amountInThisBand = remaining.subtract(taxBand.lowerBound());
                final BigDecimal contributionInThisBand = amountInThisBand.multiply(taxBand.taxRate());
                accumulatedContribution = accumulatedContribution.add(contributionInThisBand);
                remaining = remaining.subtract(amountInThisBand);
            }
        }
        return AnnualAmount.valueOf(accumulatedContribution);
    }
}
