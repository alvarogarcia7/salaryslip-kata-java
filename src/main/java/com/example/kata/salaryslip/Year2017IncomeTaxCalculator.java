package com.example.kata.salaryslip;

import java.math.BigDecimal;

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
        BigDecimal remainingGrossAnnualSalary = employee.grossAnnualSalary();
        BigDecimal taxPayable = BigDecimal.ZERO;
        if(firstIsGreaterThan(remainingGrossAnnualSalary, BigDecimal.valueOf(43000))){
            final BigDecimal inThisBand = remainingGrossAnnualSalary.subtract(BigDecimal.valueOf(43000));
            taxPayable = taxPayable.add(inThisBand.multiply(BigDecimal.valueOf(0.4)));
            remainingGrossAnnualSalary = remainingGrossAnnualSalary.subtract(inThisBand);
        }
        if (firstIsGreaterThan(remainingGrossAnnualSalary, BigDecimal.valueOf(11000))) {
            taxPayable = taxPayable.add(remainingGrossAnnualSalary.subtract(BigDecimal.valueOf(11000)).multiply(BigDecimal
                    .valueOf(0.2)));
        }
        return taxPayable;
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

}
