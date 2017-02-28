package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class SalarySlipGenerator {
    private final Console console;

    public SalarySlipGenerator (final Console console) {
        this.console = console;
    }

    public void generateFor (final Employee employee) {
        console.println("Employee ID: " + employee.employeeId());
        console.println("Employee Name: " + employee.name());
        console.println("Gross Salary: " + format(toMonthly(employee.grossAnnualSalary())));
    }

    private String format (final BigDecimal amount) {
        return "£" + amount + "0";
    }

    private BigDecimal toMonthly (final BigDecimal grossAnnualSalary) {
        return grossAnnualSalary.divide(BigDecimal.valueOf(12));
    }
}
