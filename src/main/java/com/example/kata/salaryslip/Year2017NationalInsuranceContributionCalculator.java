package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Year2017NationalInsuranceContributionCalculator implements NationalInsuranceContributionCalculator {
    @Override
    public BigDecimal amountFor (final Employee employee) {
        List<TaxBand> taxBands = new ArrayList<TaxBand>() {{
            this.add(new TaxBand(BigDecimal.valueOf(0), BigDecimal.ZERO));
            this.add(new TaxBand(BigDecimal.valueOf(8060), BigDecimal.valueOf(0.12)));
            this.add(new TaxBand(BigDecimal.valueOf(43000), BigDecimal.valueOf(0.02)));
        }};

        BigDecimal remaining = employee.grossAnnualSalary();
        BigDecimal accumulatedContribution = BigDecimal.ZERO;
        Collections.reverse(taxBands);
        for (TaxBand taxBand : taxBands) {
            if(firstIsGreaterThanSecond(remaining, taxBand.lowerBound)) {
                final BigDecimal amountInThisBand = remaining.subtract(taxBand.lowerBound);
                assert (amountInThisBand.compareTo(BigDecimal.ZERO) == 1);
                final BigDecimal contributionInThisBand = amountInThisBand.multiply(taxBand.taxRate);
                accumulatedContribution = accumulatedContribution.add(contributionInThisBand);
                remaining = remaining.subtract(amountInThisBand);
            }
        }
        return accumulatedContribution;
    }

    private boolean firstIsGreaterThanSecond (final BigDecimal annualGrossSalary, final BigDecimal val) {
        return annualGrossSalary.compareTo(val) == 1;
    }

    private class TaxBand {
        private final BigDecimal lowerBound;
        private final BigDecimal taxRate;

        public TaxBand (final BigDecimal lowerBound, final BigDecimal taxRate) {
            this.lowerBound = lowerBound;
            this.taxRate = taxRate;
        }
    }
}
