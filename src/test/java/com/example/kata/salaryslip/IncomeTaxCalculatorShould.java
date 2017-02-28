package com.example.kata.salaryslip;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class IncomeTaxCalculatorShould {

    private IncomeTaxCalculator sut;

    @Before
    public void setUp () {
        sut = new Year2017IncomeTaxCalculator();
    }

    @Test
    public void taxable_income_in_the_personal_allowance_tax_band () {
        whenSalaryIs(ZERO, expectTaxableIncome(ZERO));
        whenSalaryIs(valueOf(11000), expectTaxableIncome(ZERO));
    }

    @Test
    public void taxable_income_in_the_basic_rate_tax_band () {
        whenSalaryIs(valueOf(12000), expectTaxableIncome(valueOf(1000)));
    }

    @Test
    public void taxable_income_under_100k () {
        whenSalaryIs(valueOf(100_000), expectTaxableIncome(valueOf(89_000)));
    }

    @Test
    public void the_taxable_income_increases_by_reducing_the_personal_allowance () {
        whenSalaryIs(valueOf(105_500), expectTaxableIncome(valueOf(97_250)));
        whenSalaryIs(valueOf(111_000), expectTaxableIncome(valueOf(105_500)));
        whenSalaryIs(valueOf(122_000), expectTaxableIncome(valueOf(122_000)));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

    private Matcher<BigDecimal> expectTaxableIncome (final BigDecimal zero) {
        return Is.is(zero);
    }

    private void whenSalaryIs (final BigDecimal grossAnnualSalary, final Matcher<BigDecimal> matcher) {
        assertThat(sut.taxableIncomeFor(employeeMaking(grossAnnualSalary)), matcher);
    }

}
