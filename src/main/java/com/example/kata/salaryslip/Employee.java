package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class Employee {
    private final String employeeID;

    public Employee (final String employeeID, final String name, final BigDecimal grossAnnualSalary) {

        this.employeeID = employeeID;
    }

    public BigDecimal grossAnnualSalary () {
        return null;
    }

    public String name () {
        return null;
    }

    public String employeeId () {
        return employeeID;
    }
}
