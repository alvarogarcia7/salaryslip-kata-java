package com.example.kata.salaryslip.domain;

import java.math.BigDecimal;

public class TaxBand {
    private final BigDecimal lowerBound;
    private final BigDecimal taxRate;

    public TaxBand (final BigDecimal lowerBound, final BigDecimal taxRate) {
        this.lowerBound = lowerBound;
        this.taxRate = taxRate;
    }

    public BigDecimal lowerBound () {
        return lowerBound;
    }

    public BigDecimal taxRate () {
        return taxRate;
    }
}
