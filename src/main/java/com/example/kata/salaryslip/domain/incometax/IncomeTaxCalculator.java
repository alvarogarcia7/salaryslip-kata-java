package com.example.kata.salaryslip.domain.incometax;

import com.example.kata.salaryslip.domain.AnnualAmount;
import com.example.kata.salaryslip.domain.Employee;

public interface IncomeTaxCalculator {
    AnnualAmount taxableIncomeFor (Employee employee);

    AnnualAmount taxFreeIncomeFor (Employee employee);

    AnnualAmount taxPayableFor (Employee employee);
}
