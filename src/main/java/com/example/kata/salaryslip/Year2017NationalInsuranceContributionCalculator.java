package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Year2017NationalInsuranceContributionCalculator implements NationalInsuranceContributionCalculator {
    @Override
    public BigDecimal amountFor (final Employee employee) {
        List<TaxBand> taxBands = new ArrayList<TaxBand>() {{
            this.add(new TaxBand(BigDecimal.valueOf(8060), BigDecimal.ZERO));
            this.add(new TaxBand(BigDecimal.valueOf(43000), BigDecimal.valueOf(0.12)));
            this.add(new TaxBand(BigDecimal.valueOf(Integer.MAX_VALUE), BigDecimal.valueOf(0.02)));
        }};

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

    private class TaxBand {
        private final BigDecimal upperBound;
        private final BigDecimal taxRate;

        public TaxBand (final BigDecimal upperBound, final BigDecimal taxRate) {
            this.upperBound = upperBound;
            this.taxRate = taxRate;
        }
    }
}
