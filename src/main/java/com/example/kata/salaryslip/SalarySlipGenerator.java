package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class SalarySlipGenerator {
    private final Console console;
    private final NationalInsuranceContributionCalculator nationalInsuranceContributionCalculator;
    private final IncomeTaxCalculator incomeTaxCalculator;

    public SalarySlipGenerator (final Console console, final NationalInsuranceContributionCalculator nationalInsuranceContributionCalculator, final IncomeTaxCalculator incomeTaxCalculator) {
        this.console = console;
        this.nationalInsuranceContributionCalculator = nationalInsuranceContributionCalculator;
        this.incomeTaxCalculator = incomeTaxCalculator;
    }

    public void generateFor (final Employee employee) {
        console.println("Employee ID: " + employee.employeeId());
        console.println("Employee Name: " + employee.name());
        console.println("Gross Salary: " + format(toMonthly(employee.grossAnnualSalary())));
        console.println("National Insurance contributions: " + format(calculateNationalInsuranceContributions(employee)));
        console.println("Tax-free allowance: " + format(toMonthly(incomeTaxCalculator.taxFreeIncomeFor(employee))));
        console.println("Taxable income: " + format(toMonthly(incomeTaxCalculator.taxableIncomeFor(employee))));
        console.println("Tax payable: " + format(toMonthly(incomeTaxCalculator.taxPayableFor(employee))));
    }

    private String format (final MonthlyAmount amount) {
        return "£" + amount.value();
    }

    private AnnualAmount calculateNationalInsuranceContributions (Employee employee) {
        return nationalInsuranceContributionCalculator.amountFor(employee);
    }

    private String format (final AnnualAmount amount) {
        return "£" + amount.value();
    }

    private BigDecimal toMonthly (final BigDecimal grossAnnualSalary) {
        return grossAnnualSalary.divide(BigDecimal.valueOf(12));
    }

    private MonthlyAmount toMonthly (final AnnualAmount grossAnnualSalary) {
        return grossAnnualSalary.toMonthly();
    }
}
