package com.example.kata.salaryslip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.kata.salaryslip.BigDecimalUtils.firstIsGreaterThan;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class Year2017IncomeTaxCalculator implements IncomeTaxCalculator {

    private static final BigDecimal PERSONAL_ALLOWANCE = valueOf(11000);

    @Override
    public AnnualAmount taxableIncomeFor (final Employee employee) {
        final AnnualAmount grossAnnualSalary = employee.grossAnnualSalary();
        final BigDecimal personalAllowanceThreshold = valueOf(100_000);

        BigDecimal personalAllowance = PERSONAL_ALLOWANCE;
        personalAllowance = reducePersonalAllowance(personalAllowance, grossAnnualSalary, personalAllowanceThreshold);
        return AnnualAmount.valueOf(getNumberOrZero(grossAnnualSalary.value().subtract(personalAllowance)));
    }

    @Override
    public AnnualAmount taxFreeIncomeFor (final Employee employee) {
        final BigDecimal taxableIncome = taxableIncomeFor(employee).value();
        return AnnualAmount.valueOf(employee.grossAnnualSalary().value().subtract(taxableIncome));
    }

    @Override
    public AnnualAmount taxPayableFor (final Employee employee) {
        AnnualAmount grossAnnual = employee.grossAnnualSalary();
        final BigDecimal personalAllowanceReduction = reducePersonalAllowance(PERSONAL_ALLOWANCE,
                grossAnnual, BigDecimal.valueOf(100_000));
        List<TaxBand> taxBands = getTaxBands(PERSONAL_ALLOWANCE.subtract(personalAllowanceReduction));
        final CategoryOverflowCalculator calculator = new CategoryOverflowCalculator(taxBands);
        return calculator.forAmount(grossAnnual);
    }

    private BigDecimal reducePersonalAllowance (BigDecimal personalAllowance, final AnnualAmount grossAnnualSalary, final BigDecimal personalAllowanceThreshold) {
        if (firstIsGreaterThan(grossAnnualSalary.value(), personalAllowanceThreshold)) {
            personalAllowance = getNumberOrZero(personalAllowance.subtract(grossAnnualSalary.value().subtract
                    (personalAllowanceThreshold).divide(valueOf(2))));
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

    private List<TaxBand> getTaxBands (BigDecimal reductionInPersonalAllowance) {
        List<TaxBand> taxBands = new ArrayList<TaxBand>() {{
            this.add(new TaxBand(valueOf(0), ZERO));
            this.add(new TaxBand(valueOf(11_000).subtract(reductionInPersonalAllowance), valueOf(0.2)));
            this.add(new TaxBand(valueOf(43_000).subtract(reductionInPersonalAllowance), valueOf(0.4)));
            this.add(new TaxBand(valueOf(150_000), valueOf(0.45)));
        }};
        Collections.reverse(taxBands);
        return taxBands;
    }

}
