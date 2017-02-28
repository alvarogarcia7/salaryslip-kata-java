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

    @Before
    public void setUp () {
        context = new Mockery();
        console = context.mock(Console.class);
        nationalInsuranceContributionCalculator = context.mock(NationalInsuranceContributionCalculator.class);
        sut = new SalarySlipGenerator(console, nationalInsuranceContributionCalculator);
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));
    }


    @Test
    public void print_the_employeeId () {
        context.checking(new Expectations() {{
            oneOf(console).println("Employee ID: 12345");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class)));
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_name () {
        context.checking(new Expectations() {{
            oneOf(console).println("Employee Name: John J Doe");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class)));
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_monthly_gross_salary () {
        context.checking(new Expectations() {{
            oneOf(console).println("Gross Salary: £2000.00");
            allowing(console).println(with(any(String.class)));
            allowing(nationalInsuranceContributionCalculator).amountFor(with(any(Employee.class)));
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
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }
}
