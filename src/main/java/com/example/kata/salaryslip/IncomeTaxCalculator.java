package com.example.kata.salaryslip;

public interface IncomeTaxCalculator {
    AnnualAmount taxableIncomeFor (Employee employee);

    AnnualAmount taxFreeIncomeFor (Employee employee);

    AnnualAmount taxPayableFor (Employee employee);
}
