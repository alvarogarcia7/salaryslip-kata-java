package com.example.kata.salaryslip;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NationalInsuranceContributionCalculatorShould {

    private NationalInsuranceContributionCalculator sut;

    @Before
    public void setUp () {
        sut = new Year2017NationalInsuranceContributionCalculator();
    }

    @Test
    public void calculate_for_no_contributions_band () {
        assertThat(sut.amountFor(employeeMaking(BigDecimal.ZERO)),  is(ZERO));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
