package com.company.projekt.service;

import com.company.projekt.model.Rate;
import com.company.projekt.model.Summary;

import java.math.BigDecimal;
import java.util.List;

public class SummaryServiceFactory {
    public static SummaryService create() {
        return rates -> {
            BigDecimal interestSum = calculate(rates, rate -> rate.getRateAmounts()
                    .getInterestAmount());
            BigDecimal provision = calculate(rates, rate -> rate.getRateAmounts()
                    .getOverpayment()
                    .getProvisionAmount());
            BigDecimal totalLost = totalLost(interestSum, provision);
            return new Summary(interestSum, provision, totalLost);
        };
    }

    private static BigDecimal totalLost(BigDecimal interestSum, BigDecimal provision) {
        return interestSum.add(provision);
    }

    private static BigDecimal calculate(List<Rate> rates, Function function) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Rate rate : rates) {
            sum = sum.add(function.calculate(rate));
        }
        return sum;
    }

}
