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
    public void personal_allowance () {
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.ZERO)), is(BigDecimal.ZERO));
        assertThat(sut.taxableIncomeFor(employeeMaking(BigDecimal.valueOf(11000))), is(BigDecimal.ZERO));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
