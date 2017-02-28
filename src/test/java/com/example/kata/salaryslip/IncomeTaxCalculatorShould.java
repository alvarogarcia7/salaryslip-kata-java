package com.example.kata.salaryslip;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IncomeTaxCalculatorShould {

    private IncomeTaxCalculator sut;

    @Before
    public void setUp () {
        sut = new Year2017IncomeTaxCalculator();
    }

    @Test
    public void taxable_income_in_the_personal_allowance_tax_band () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.ZERO)), is(BigDecimal.ZERO));
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(11000))), is(BigDecimal.ZERO));
    }

    @Test
    public void taxable_income_in_the_basic_rate_tax_band () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(12000))), is(BigDecimal.valueOf(1000)));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
