package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.kata.salaryslip.BigDecimalUtils.*;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class Year2017NationalInsuranceContributionCalculator implements NationalInsuranceContributionCalculator {
    @Override
    public BigDecimal amountFor (final Employee employee) {
        List<TaxBand> taxBands = getTaxBands();

        BigDecimal remaining = employee.grossAnnualSalary();
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

    private List<TaxBand> getTaxBands () {
        List<TaxBand> taxBands = new ArrayList<TaxBand>() {{
            this.add(new TaxBand(valueOf(0), ZERO));
            this.add(new TaxBand(valueOf(8060), valueOf(0.12)));
            this.add(new TaxBand(valueOf(43000), valueOf(0.02)));
        }};
        Collections.reverse(taxBands);
        return taxBands;
    }

}
