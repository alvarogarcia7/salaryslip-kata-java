package com.example.kata.salaryslip;

import org.hamcrest.core.Is;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnnualAmountHelper {
    void assertSameValueFor (final AnnualAmount expected, final AnnualAmount actual) {
        if (!actual.isEqual(expected)) {
            assertThat(actual, Is.is(expected));
        }
        assertThat(actual.isEqual(expected), is(true));
    }
}
