package com.example.kata.salaryslip.unit.nationalinsurance;

import com.example.kata.salaryslip.AnnualAmountHelper;
import com.example.kata.salaryslip.domain.AnnualAmount;
import com.example.kata.salaryslip.domain.Employee;
import com.example.kata.salaryslip.domain.nationalinsurance.NationalInsuranceContributionCalculator;
import com.example.kata.salaryslip.domain.nationalinsurance.Year2017NationalInsuranceContributionCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class NationalInsuranceContributionCalculator_ForLowerBand {

    private final String description;
    private NationalInsuranceContributionCalculator sut;
    private AnnualAmountHelper annualAmountHelper = new AnnualAmountHelper();

    @Before
    public void setUp () {
        sut = new Year2017NationalInsuranceContributionCalculator();
    }


    @Parameters(name = "{index} {0}: NI contribution for grossAnnualSalary={1} is " +
            "expectedContribution={2}")
    public static Collection<Object[]> generateData () {
        return Arrays.asList(new Object[][]{
                {"Amount is zero", ZERO, ZERO},
                {"Amount is under the threshold", BigDecimal.valueOf(1), ZERO},
                {"Amount is under the threshold", BigDecimal.valueOf(2), ZERO},
                {"Amount is under the threshold", BigDecimal.valueOf(8000), ZERO},
                {"Amount is under the threshold", BigDecimal.valueOf(8059.99), ZERO},
                {"Amount is equal to the threshold", BigDecimal.valueOf(8060), ZERO},
        });
    }

    private BigDecimal grossAnnualSalary;
    private BigDecimal expectedContribution;

    public NationalInsuranceContributionCalculator_ForLowerBand (String description, BigDecimal grossAnnualSalary,
                                                                 BigDecimal expectedContribution) {

        this.description = description;
        this.grossAnnualSalary = grossAnnualSalary;
        this.expectedContribution = expectedContribution;
    }

    @Test
    public void test () {
        final AnnualAmount actual = sut.amountFor(employeeMaking(grossAnnualSalary));
        assertThat(actual.value().compareTo(expectedContribution), is(0));
    }

    private Employee employeeMaking (final BigDecimal grossAnnualSalary) {
        return new Employee("", "", grossAnnualSalary);
    }

}
