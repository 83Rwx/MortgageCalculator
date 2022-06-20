package com.company.projekt.utils;

import com.company.projekt.model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {

    public static final BigDecimal YEAR = BigDecimal.valueOf(12);

    private CalculationUtils() {
    }

    public static BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent)
                .divide(YEAR, 50, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateResidualAmount(RateAmounts rateAmounts, BigDecimal amount) {
        return amount.subtract(rateAmounts.getCapitalAmount())
                .subtract(rateAmounts.getOverpayment()
                        .getAmount())
                .max(BigDecimal.ZERO);
    }
}
