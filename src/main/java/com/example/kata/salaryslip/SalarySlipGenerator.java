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
        console.println("Gross Salary: £" + employee.grossAnnualSalary().divide(BigDecimal.valueOf(12))+"0");
    }
}
