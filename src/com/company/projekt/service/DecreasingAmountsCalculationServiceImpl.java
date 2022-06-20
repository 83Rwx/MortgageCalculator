package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Overpayment;
import com.company.projekt.model.Rate;
import com.company.projekt.model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.company.projekt.utils.CalculationUtils.calculateInterestAmount;

public class DecreasingAmountsCalculationServiceImpl implements DecreasingAmountsCalculationService {

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment) {
        BigDecimal referenceAmount = inputData.getAmount();
        BigDecimal referenceDuration = inputData.getMonthsDuration();
        BigDecimal interestPercent = inputData.getInterestPercent();

        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal interestAmount = calculateInterestAmount(referenceAmount, interestPercent);
        BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate) {
        BigDecimal referenceAmount = previousRate.getMortgageReference()
                .getReferenceAmount();
        BigDecimal referenceDuration = previousRate.getMortgageReference()
                .getReferenceDuration();
        BigDecimal residualAmount = previousRate.getMortgageResidual()
                .getAmount();
        BigDecimal interestPercent = inputData.getInterestPercent();


        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateCapitalAmount(BigDecimal amount, BigDecimal monthsDuration, BigDecimal residualAmount) {
        BigDecimal capitalAmount = amount.divide(monthsDuration, 50, RoundingMode.HALF_UP);

        return capitalAmount.compareTo(residualAmount) >= 0 ? residualAmount : capitalAmount;
    }

}
