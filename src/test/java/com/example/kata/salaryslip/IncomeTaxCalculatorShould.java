package com.example.kata.salaryslip;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class IncomeTaxCalculatorShould {

    private AnnualAmountHelper annualAmountHelper = new AnnualAmountHelper();

    private IncomeTaxCalculator sut;

    @Before
    public void setUp () {
        sut = new Year2017IncomeTaxCalculator();
    }

    @Test
    public void taxable_income_in_the_personal_allowance_tax_band () {
        anEmployeeMaking(ZERO).expectTaxableIncome(ZERO);
        anEmployeeMaking(valueOf(11000)).expectTaxableIncome(ZERO);
    }

    @Test
    public void taxable_income_in_the_basic_rate_tax_band () {
        anEmployeeMaking(valueOf(12000)).expectTaxableIncome(valueOf(1000));
    }

    @Test
    public void taxable_income_under_100k () {
        anEmployeeMaking(valueOf(100_000)).expectTaxableIncome(valueOf(89_000));
    }

    @Test
    public void the_taxable_income_increases_by_reducing_the_personal_allowance () {
        anEmployeeMaking(valueOf(105_500)).expectTaxableIncome(valueOf(97_250));
        anEmployeeMaking(valueOf(111_000)).expectTaxableIncome(valueOf(105_500));
        anEmployeeMaking(valueOf(122_000)).expectTaxableIncome(valueOf(122_000));
    }

    @Test
    public void the_taxable_income_becomes_the_gross_when_the_personal_allowance_has_been_reduced_to_zero () {
        anEmployeeMaking(valueOf(130_000)).expectTaxableIncome(valueOf(130_000));
    }

    @Test
    public void the_taxfree_income_is_the_part_not_covered_by_the_taxable_income () {
        anEmployeeMaking(valueOf(130_000)).expectTaxFreeIncome(valueOf(0));
        anEmployeeMaking(valueOf(105_500)).expectTaxFreeIncome(valueOf(105_500).subtract(BigDecimal.valueOf(97_250)));
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

    @Test
    public void tax_payable_in_the_higher_rate_band() {
        whenSalaryIsTaxPayable(valueOf(45_000), valueOf(7_200));
        whenSalaryIsTaxPayable(valueOf(50_000), valueOf(9_200));
//        whenSalaryIsTaxPayable(valueOf(150_000), valueOf(53_600));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

    private AnnualAmount expectTaxFreeIncome (final BigDecimal value) {
        return AnnualAmount.valueOf(value);
    }

    private TestShell anEmployeeMaking (final BigDecimal grossAnnualSalary) {
        return new TestShell(employeeMaking(grossAnnualSalary));
    }

    private void whenSalaryIsTaxPayable (final BigDecimal grossAnnualSalary, final BigDecimal expected) {
        annualAmountHelper.assertSameValueFor(AnnualAmount.valueOf(expected), sut.taxPayableFor(employeeMaking(grossAnnualSalary)));
    }

    private class TestShell {
        private final Employee employee;

        public TestShell (final Employee employee) {
            this.employee = employee;
        }

        public void expectTaxableIncome (final BigDecimal zero) {
            annualAmountHelper.assertSameValueFor(AnnualAmount.valueOf(zero), sut.taxableIncomeFor(employee));
        }

        public void expectTaxFreeIncome (final BigDecimal expected) {
            annualAmountHelper.assertSameValueFor(AnnualAmount.valueOf(expected), sut.taxFreeIncomeFor(employee));
        }

    }
}
