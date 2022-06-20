package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.MortgageResidual;
import com.company.projekt.model.Rate;
import com.company.projekt.model.RateAmounts;
import com.company.projekt.utils.CalculationUtils;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl implements ResidualCalculationService {

    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData) {
        if (BigDecimal.ZERO.equals(inputData.getAmount())) {
            return new MortgageResidual(BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            BigDecimal residualAmount = CalculationUtils.calculateResidualAmount(rateAmounts, inputData.getAmount());
            BigDecimal residualDuration = inputData.getMonthsDuration()
                    .subtract(BigDecimal.ONE);

            return new MortgageResidual(residualAmount, residualDuration);
        }
    }

    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate) {
        MortgageResidual residual = previousRate.getMortgageResidual();

        BigDecimal residualAmount = CalculationUtils.calculateResidualAmount(rateAmounts, residual.getAmount());
        BigDecimal residualDuration = residual.getDuration()
                .subtract(BigDecimal.ONE);

        return new MortgageResidual(residualAmount, residualDuration);
    }

    private BigDecimal calculateResidualAmount(RateAmounts rateAmounts, BigDecimal amount) {
        return amount.subtract(rateAmounts.getCapitalAmount())
                .subtract(rateAmounts.getOverpayment()
                        .getAmount())
                .max(BigDecimal.ZERO);
    }
}
