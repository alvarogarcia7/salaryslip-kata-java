package com.example.kata.salaryslip.domain.nationalinsurance;

import com.example.kata.salaryslip.domain.AnnualAmount;
import com.example.kata.salaryslip.domain.Employee;

public interface NationalInsuranceContributionCalculator {
    AnnualAmount amountFor (Employee employee);
}
