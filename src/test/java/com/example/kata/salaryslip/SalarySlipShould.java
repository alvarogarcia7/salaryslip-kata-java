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

    @Before
    public void setUp () {
        context = new Mockery();
        console = context.mock(Console.class);
        sut = new SalarySlipGenerator(console);
        employee = new Employee("12345", "John J Doe", BigDecimal.valueOf(24000.00));
    }


    @Test
    public void print_the_employeeId () {
        context.checking(new Expectations() {{
            oneOf(console).println("Employee ID: 12345");
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }

    @Test
    public void print_the_name () {
        context.checking(new Expectations() {{
            oneOf(console).println("Employee Name: John J Doe");
        }});

        sut.generateFor(employee);

        context.assertIsSatisfied();
    }
}
