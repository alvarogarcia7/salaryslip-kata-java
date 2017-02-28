package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class SalarySlipGenerator {
    private final Console console;
    private final NationalInsuranceContributionCalculator nationalInsuranceContributionCalculator;

    public SalarySlipGenerator (final Console console, final NationalInsuranceContributionCalculator nationalInsuranceContributionCalculator) {
        this.console = console;
        this.nationalInsuranceContributionCalculator = nationalInsuranceContributionCalculator;
    }

    public void generateFor (final Employee employee) {
        console.println("Employee ID: " + employee.employeeId());
        console.println("Employee Name: " + employee.name());
        console.println("Gross Salary: " + format(toMonthly(employee.grossAnnualSalary())));
        console.println("National Insurance contributions: " + format(calculateNationalInsuranceContributions(employee)));
    }

    private BigDecimal calculateNationalInsuranceContributions (Employee employee) {
        return nationalInsuranceContributionCalculator.amountFor(employee);
    }

    private String format (final BigDecimal amount) {
        return "£" + amount + "0";
    }

    private BigDecimal toMonthly (final BigDecimal grossAnnualSalary) {
        return grossAnnualSalary.divide(BigDecimal.valueOf(12));
    }
}
