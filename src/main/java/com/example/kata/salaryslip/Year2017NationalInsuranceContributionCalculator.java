package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class Year2017NationalInsuranceContributionCalculator implements NationalInsuranceContributionCalculator {
    @Override
    public BigDecimal amountFor (final Employee employee) {
        List<TaxBand> taxBands = getTaxBands();
        return new CategoryOverflowCalculator(taxBands).forAmount(employee.grossAnnualSalary());
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
