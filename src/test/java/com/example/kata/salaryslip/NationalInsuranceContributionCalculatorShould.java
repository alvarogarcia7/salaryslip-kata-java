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
    public void calculate_for_no_contributions_band_lower_bound () {
        assertThat(sut.amountFor(employeeMaking(BigDecimal.ZERO)), is(ZERO));
    }

    @Test
    public void calculate_for_no_contributions_band_higher_bound () {
        assertThat(sut.amountFor(employeeMaking(BigDecimal.valueOf(8060.00))), is(ZERO));
    }

    @Test
    public void calculate_12_percent_for_contributions_in_the_basic_contributions_band_lower_treshold () {
        final BigDecimal basicContributions = BigDecimal.ONE.multiply(BigDecimal.valueOf(0.12));
        equalBigDecimalValues(sut.amountFor(employeeMaking(BigDecimal.valueOf(8061.00))), basicContributions);
    }

    @Test
    public void calculate_12_percent_for_contributions_in_the_basic_contributions_band_upper_treshold () {
        final BigDecimal basicContributionsAmount = BigDecimal.valueOf(43000).subtract(BigDecimal.valueOf(8060));
        final BigDecimal basicContributions = basicContributionsAmount.multiply(BigDecimal.valueOf(0.12));
        equalBigDecimalValues(sut.amountFor(employeeMaking(BigDecimal.valueOf(43000.00))), basicContributions);
    }

    private void equalBigDecimalValues (final BigDecimal actual, final BigDecimal expected) {
        assertThat(actual.compareTo(expected), is(0));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
