package com.example.kata.salaryslip;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

public class IncomeTaxCalculatorShould {

    private IncomeTaxCalculator sut;

    @Before
    public void setUp () {
        sut = new Year2017IncomeTaxCalculator();
    }

    @Test
    public void taxable_income_in_the_personal_allowance_tax_band () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.ZERO)), Is.is(BigDecimal.ZERO));
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(11000))), Is.is(BigDecimal.ZERO));
    }

    @Test
    public void taxable_income_in_the_basic_rate_tax_band () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(12000))), Is.is(BigDecimal.valueOf(1000)));
    }

    @Test
    public void taxable_income_under_100k () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(100_000))), Is.is(BigDecimal.valueOf(89_000)));
    }

    @Test
    public void the_taxable_income_increases_by_reducing_the_personal_allowance () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(105_500))), Is.is(BigDecimal.valueOf(97_250)));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
