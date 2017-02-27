package com.example.kata.salaryslip;

public class SalarySlipGenerator {
    private final Console console;

    public SalarySlipGenerator (final Console console) {
        this.console = console;
    }

    public void generateFor (final Employee employee) {
        console.println("Employee ID: " + employee.employeeId());
    }
}
