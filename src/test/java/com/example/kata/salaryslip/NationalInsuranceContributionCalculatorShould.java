package com.example.kata.salaryslip;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
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
        final AnnualAmount actual = sut.amountFor(employeeMaking(ZERO));
        assertThat(actual.value().compareTo(ZERO), is(0));
    }

    @Test
    public void calculate_for_no_contributions_band_higher_bound () {
        equalBigDecimalValues(sut.amountFor(employeeMaking(valueOf(8060.00))), ZERO);
    }

    @Test
    public void calculate_12_percent_for_contributions_in_the_basic_contributions_band_lower_threshold () {
        final BigDecimal basicContributions = ONE.multiply(valueOf(0.12));
        equalBigDecimalValues(sut.amountFor(employeeMaking(valueOf(8061.00))), basicContributions);
    }

    @Test
    public void calculate_12_percent_for_contributions_in_the_basic_contributions_band_upper_threshold () {
        final BigDecimal basicContributionsAmount = valueOf(43000).subtract(valueOf(8060));
        final BigDecimal basicContributions = basicContributionsAmount.multiply(valueOf(0.12));
        equalBigDecimalValues(sut.amountFor(employeeMaking(valueOf(43000.00))), basicContributions);
    }

    @Test
    public void
    calculate_12_percent_for_contributions_plus_2_percent_for_higher_contributions_in_the_higher_contributions_band_lower_threshold (){
        final BigDecimal basicContributionsAmount = valueOf(43000).subtract(valueOf(8060));
        final BigDecimal basicContributions = basicContributionsAmount.multiply(valueOf(0.12));

        final BigDecimal higherContributionsAmount = ONE;
        final BigDecimal higherContributions = higherContributionsAmount.multiply(valueOf(0.02));


        equalBigDecimalValues(sut.amountFor(employeeMaking(valueOf(43001.00))), basicContributions.add(higherContributions));
    }

    @Test
    public void
    calculate_12_percent_for_contributions_plus_2_percent_for_higher_contributions_in_the_higher_contributions_band_another_amount() {
        final BigDecimal basicContributionsAmount = valueOf(43000).subtract(valueOf(8060));
        final BigDecimal basicContributions = basicContributionsAmount.multiply(valueOf(0.12));

        final BigDecimal higherContributionsAmount = valueOf(45001).subtract(valueOf(43000));
        final BigDecimal higherContributions = higherContributionsAmount.multiply(valueOf(0.02));


        equalBigDecimalValues(sut.amountFor(employeeMaking(valueOf(45001.00))), basicContributions.add
                (higherContributions));
    }

    private void equalBigDecimalValues (final AnnualAmount actual, final BigDecimal expected) {
        assertThat(actual.value().compareTo(expected), is(0));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
