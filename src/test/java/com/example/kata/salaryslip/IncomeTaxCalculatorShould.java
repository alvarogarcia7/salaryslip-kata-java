package com.example.kata.salaryslip;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
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

    @Test
    public void the_taxable_income_becomes_the_gross_when_the_personal_allowance_has_been_reduced_to_zero () {
        whenSalaryIs(valueOf(130_000), expectTaxableIncome(valueOf(130_000)));
    }

    @Test
    public void the_taxfree_income_is_the_part_not_covered_by_the_taxable_income () {
        whenSalaryIsTaxFree(valueOf(130_000), expectTaxFreeIncome(valueOf(0)));
        whenSalaryIsTaxFree(valueOf(105_500), expectTaxFreeIncome(valueOf(105_500).subtract(BigDecimal.valueOf(97_250))));
    }

    @Test
    public void tax_payable_in_the_personal_allowance_band () {
        whenSalaryIsTaxPayable(valueOf(0), valueOf(0));
        whenSalaryIsTaxPayable(valueOf(5000), valueOf(0));
        whenSalaryIsTaxPayable(valueOf(11000), valueOf(0));
    }

    @Test
    public void tax_payable_in_the_basic_rate_band() {
        whenSalaryIsTaxPayable(valueOf(12_000), valueOf(200));
        whenSalaryIsTaxPayable(valueOf(40_000), valueOf(5_800));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

    private Matcher<BigDecimal> expectTaxableIncome (final BigDecimal zero) {
        return is(zero);
    }

    private Matcher<BigDecimal> expectTaxFreeIncome (final BigDecimal zero) {
        return is(zero);
    }

    private void whenSalaryIs (final BigDecimal grossAnnualSalary, final Matcher<BigDecimal> matcher) {
        assertThat(sut.taxableIncomeFor(employeeMaking(grossAnnualSalary)), matcher);
    }

    private void whenSalaryIsTaxFree (final BigDecimal grossAnnualSalary, final Matcher<BigDecimal> matcher) {
        assertThat(sut.taxFreeIncomeFor(employeeMaking(grossAnnualSalary)), matcher);
    }

    private void whenSalaryIsTaxPayable (final BigDecimal grossAnnualSalary, final BigDecimal expected) {
        final BigDecimal actual = sut.taxPayableFor(employeeMaking(grossAnnualSalary));
        assertThat(areEqual(actual, expected), is(true));
    }

    private boolean areEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }
}
