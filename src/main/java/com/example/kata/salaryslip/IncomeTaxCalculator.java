package com.example.kata.salaryslip;

import java.math.BigDecimal;

public interface IncomeTaxCalculator {
    BigDecimal taxableIncomeFor (Employee employee);

    BigDecimal taxFreeIncomeFor (Employee employee);
}
