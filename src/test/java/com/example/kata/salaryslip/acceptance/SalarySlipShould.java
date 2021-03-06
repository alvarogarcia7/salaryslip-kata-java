package com.example.kata.salaryslip.acceptance;

import com.example.kata.salaryslip.delivery.Console;
import com.example.kata.salaryslip.domain.Employee;
import com.example.kata.salaryslip.domain.incometax.IncomeTaxCalculator;
import com.example.kata.salaryslip.domain.incometax.Year2017IncomeTaxCalculator;
import com.example.kata.salaryslip.domain.nationalinsurance.NationalInsuranceContributionCalculator;
import com.example.kata.salaryslip.domain.nationalinsurance.Year2017NationalInsuranceContributionCalculator;
import com.example.kata.salaryslip.domain.salaryslip.SalarySlipGenerator;
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
        incomeTaxCalculator= new Year2017IncomeTaxCalculator();
        nationalInsuranceContributionCalculator = new Year2017NationalInsuranceContributionCalculator();
        sut = new SalarySlipGenerator(console, nationalInsuranceContributionCalculator, incomeTaxCalculator);
    }


    @Test
    public void print_the_salary_slip () {
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));
        context.checking(new Expectations() {{
            oneOf(console).println("Employee ID: 12345");
            oneOf(console).println("Employee Name: John J Doe");
            oneOf(console).println("Gross Salary: £2000.00");
            oneOf(console).println("National Insurance contributions: £159.40");
            oneOf(console).println("Tax-free allowance: £916.67");
            oneOf(console).println("Taxable income: £1083.33");
            oneOf(console).println("Tax payable: £216.67");
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }
}
