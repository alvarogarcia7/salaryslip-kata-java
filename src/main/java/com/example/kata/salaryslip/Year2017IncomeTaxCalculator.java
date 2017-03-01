package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.kata.salaryslip.BigDecimalUtils.*;
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

    @Override
    public BigDecimal taxFreeIncomeFor (final Employee employee) {
        final BigDecimal taxableIncome = taxableIncomeFor(employee);
        return employee.grossAnnualSalary().subtract(taxableIncome);
    }

    @Override
    public BigDecimal taxPayableFor (final Employee employee) {
        List<TaxBand> taxBands = getTaxBands();
        final CategoryOverflowCalculator calculator = new CategoryOverflowCalculator(taxBands);
        return calculator.forAmount(employee.grossAnnualSalary());
    }

    private BigDecimal reducePersonalAllowance (BigDecimal personalAllowance, final BigDecimal grossAnnualSalary, final BigDecimal personalAllowanceThreshold) {
        if(firstIsGreaterThan(grossAnnualSalary, personalAllowanceThreshold)) {
            personalAllowance = personalAllowance.subtract(grossAnnualSalary.subtract(personalAllowanceThreshold).divide(valueOf(2)));
            personalAllowance = getNumberOrZero(personalAllowance);
        }
        return personalAllowance;
    }

    private BigDecimal getNumberOrZero (BigDecimal personalAllowance) {
        if (isNegative(personalAllowance)) {
            personalAllowance = BigDecimal.ZERO;
        }
        return personalAllowance;
    }

    private boolean isNegative (final BigDecimal personalAllowance) {
        return firstIsGreaterThan(BigDecimal.ZERO, personalAllowance);
    }

    private List<TaxBand> getTaxBands () {
        List<TaxBand> taxBands = new ArrayList<TaxBand>() {{
            this.add(new TaxBand(valueOf(0), ZERO));
            this.add(new TaxBand(valueOf(11_000), valueOf(0.2)));
            this.add(new TaxBand(valueOf(43_000), valueOf(0.4)));
        }};
        Collections.reverse(taxBands);
        return taxBands;
    }

}
