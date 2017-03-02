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
    private static final BigDecimal PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD = BigDecimal.valueOf(100_000);

    @Override
    public AnnualAmount taxableIncomeFor (final Employee employee) {
        final AnnualAmount grossAnnualSalary = employee.grossAnnualSalary();
        BigDecimal personalAllowance = reducePersonalAllowance(grossAnnualSalary);
        return AnnualAmount.valueOf(cannotBeNegative(grossAnnualSalary.value().subtract(personalAllowance)));
    }

    @Override
    public AnnualAmount taxFreeIncomeFor (final Employee employee) {
        final BigDecimal taxableIncome = taxableIncomeFor(employee).value();
        return AnnualAmount.valueOf(employee.grossAnnualSalary().value().subtract(taxableIncome));
    }

    @Override
    public AnnualAmount taxPayableFor (final Employee employee) {
        AnnualAmount grossAnnual = employee.grossAnnualSalary();
        final BigDecimal personalAllowanceReduction = reducePersonalAllowance(grossAnnual);
        List<TaxBand> taxBands = getTaxBands(PERSONAL_ALLOWANCE.subtract(personalAllowanceReduction));
        final CategoryOverflowCalculator calculator = new CategoryOverflowCalculator(taxBands);
        return calculator.forAmount(grossAnnual);
    }

    private BigDecimal reducePersonalAllowance (final AnnualAmount grossAnnualSalary) {
        BigDecimal personalAllowance = PERSONAL_ALLOWANCE;
        if (firstIsGreaterThan(grossAnnualSalary.value(), PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD)) {
            final BigDecimal amountOverThreshold = grossAnnualSalary.value().subtract(PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD);
            final BigDecimal reduceInOneForEveryTwoUnits = amountOverThreshold.divide(valueOf(2));
            personalAllowance = cannotBeNegative(personalAllowance.subtract(reduceInOneForEveryTwoUnits));
        }
        return personalAllowance;
    }

    private BigDecimal cannotBeNegative (BigDecimal personalAllowance) {
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
            this.add(new TaxBand(PERSONAL_ALLOWANCE.subtract(reductionInPersonalAllowance), valueOf(0.2)));
            this.add(new TaxBand(valueOf(43_000).subtract(reductionInPersonalAllowance), valueOf(0.4)));
            this.add(new TaxBand(valueOf(150_000), valueOf(0.45)));
        }};
        Collections.reverse(taxBands);
        return taxBands;
    }

}
