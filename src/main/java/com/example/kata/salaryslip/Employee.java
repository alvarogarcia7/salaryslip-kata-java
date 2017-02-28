package com.example.kata.salaryslip;

import java.math.BigDecimal;

public class Employee {
    private final String employeeID;
    private final String name;

    public Employee (final String employeeID, final String name, final BigDecimal grossAnnualSalary) {

        this.employeeID = employeeID;
        this.name = name;
        
    }

    public BigDecimal grossAnnualSalary () {
        return null;
    }

    public String name () {
        return name;
    }

    public String employeeId () {
        return employeeID;
    }
}
