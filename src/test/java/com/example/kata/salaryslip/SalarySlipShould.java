package com.example.kata.salaryslip;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class SalarySlipShould {

    private Console console;
    private Mockery context;
    private SalarySlipGenerator sut;
    private Employee employee;
    private NationalInsuranceContributionCalculator nationalInsuranceContributionCalculator;
    private IncomeTaxCalculator incomeTaxCalculator;

    @Before
    public void setUp () {
        context = new Mockery();
        console = context.mock(Console.class);
        nationalInsuranceContributionCalculator = context.mock(NationalInsuranceContributionCalculator.class);
        incomeTaxCalculator = context.mock(IncomeTaxCalculator.class);
        sut = new SalarySlipGenerator(console, nationalInsuranceContributionCalculator, incomeTaxCalculator);
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));
    }


    @Test
    public void print_the_employeeId () {
        context.checking(new Expectations() {{
            oneOf(console).println("Employee ID: 12345");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class))); will(returnValue
                    (BigDecimal.valueOf(159.40)));
            allowingAnyInteractionWith(this, incomeTaxCalculator);
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }


    @Test
    public void print_the_name () {
        context.checking(new Expectations() {{
            oneOf(console).println("Employee Name: John J Doe");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class))); will(returnValue
                    (BigDecimal.valueOf(159.40)));
            allowingAnyInteractionWith(this, incomeTaxCalculator);
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_monthly_gross_salary () {
        context.checking(new Expectations() {{
            oneOf(console).println("Gross Salary: £2000.00");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class))); will(returnValue
                    (BigDecimal.valueOf(159.40)));
            allowingAnyInteractionWith(this, incomeTaxCalculator);
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_national_insurance_contribution () {
        context.checking(new Expectations() {{
            oneOf(console).println("National Insurance contributions: £159.40");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class))); will(returnValue
                    (BigDecimal.valueOf(159.40)));
            allowingAnyInteractionWith(this, incomeTaxCalculator);
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_taxable_income () {

        nationalInsuranceContributionCalculator = new Year2017NationalInsuranceContributionCalculator();
        sut = new SalarySlipGenerator(console, nationalInsuranceContributionCalculator, incomeTaxCalculator);
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));


        context.checking(new Expectations() {{
            oneOf(console).println("Taxable income: £1083.33");
            allowing(console).println(with(any(String.class)));
            allowing(incomeTaxCalculator).taxableIncomeFor(with(any(Employee.class))); will(returnValue(BigDecimal.valueOf(1083.33)));
            allowing(incomeTaxCalculator).taxFreeIncomeFor(with(any(Employee.class))); will(returnValue(BigDecimal
                    .valueOf(0)));
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_taxfree_allowance () {

        nationalInsuranceContributionCalculator = new Year2017NationalInsuranceContributionCalculator();
        sut = new SalarySlipGenerator(console, nationalInsuranceContributionCalculator, incomeTaxCalculator);
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));


        context.checking(new Expectations() {{
            oneOf(console).println("Tax-free allowance: £916.67");
            allowing(console).println(with(any(String.class)));
            allowing(incomeTaxCalculator).taxableIncomeFor(with(any(Employee.class)));
            will(returnValue(BigDecimal.ZERO));
            allowing(incomeTaxCalculator).taxFreeIncomeFor(with(any(Employee.class)));
            will(returnValue(BigDecimal
                    .valueOf(916.67).multiply(BigDecimal.valueOf(12))));

        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_tax_payable () {

        nationalInsuranceContributionCalculator = new Year2017NationalInsuranceContributionCalculator();
        sut = new SalarySlipGenerator(console, nationalInsuranceContributionCalculator, incomeTaxCalculator);
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));


        context.checking(new Expectations() {{
            oneOf(console).println("Tax payable: £216.67");
            allowing(console).println(with(any(String.class)));
            allowing(incomeTaxCalculator).taxableIncomeFor(with(any(Employee.class)));
            will(returnValue(BigDecimal.ZERO));
            allowing(incomeTaxCalculator).taxFreeIncomeFor(with(any(Employee.class)));
            will(returnValue(BigDecimal.ZERO));
            oneOf(incomeTaxCalculator).taxPayableFor(with(any(Employee.class)));
            will(returnValue(BigDecimal
                    .valueOf(216.67).multiply(BigDecimal.valueOf(12))));

        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }


    private void allowingAnyInteractionWith (final Expectations expectations, final IncomeTaxCalculator incomeTaxCalculator) {
        expectations.allowing(incomeTaxCalculator).taxableIncomeFor(expectations.with(expectations.any(Employee
                .class)));
        expectations.will(expectations.returnValue(BigDecimal.ZERO));

        expectations.allowing(incomeTaxCalculator).taxFreeIncomeFor(expectations.with(expectations.any(Employee
                .class)));
        expectations.will(expectations.returnValue(BigDecimal.ZERO));
    }
}
