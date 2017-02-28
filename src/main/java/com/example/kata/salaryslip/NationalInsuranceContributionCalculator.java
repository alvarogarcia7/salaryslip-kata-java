package com.example.kata.salaryslip;

import java.math.BigDecimal;

public interface NationalInsuranceContributionCalculator {
    BigDecimal amountFor (Employee employee);
}
