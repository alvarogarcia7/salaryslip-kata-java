package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class Employee {
    private final String employeeID;
    private final String name;
    private final AnnualAmount grossAnnualSalary;

    public Employee (final String employeeID, final String name, final BigDecimal grossAnnualSalary) {

        this.employeeID = employeeID;
        this.name = name;
        this.grossAnnualSalary = AnnualAmount.valueOf(grossAnnualSalary);
    }

    public AnnualAmount grossAnnualSalary () {
        return grossAnnualSalary;
    }

    public String name () {
        return name;
    }

    public String employeeId () {
        return employeeID;
    }
}
