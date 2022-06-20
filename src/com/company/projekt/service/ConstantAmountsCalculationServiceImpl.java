package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Overpayment;
import com.company.projekt.model.Rate;
import com.company.projekt.model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.company.projekt.utils.CalculationUtils.YEAR;
import static com.company.projekt.utils.CalculationUtils.calculateInterestAmount;

public class ConstantAmountsCalculationServiceImpl implements ConstantAmountsCalculationService {

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment) {
        BigDecimal referenceAmount = inputData.getAmount();
        BigDecimal referenceDuration = inputData.getMonthsDuration();
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal q = calculateQ(inputData.getInterestPercent());

        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);

        BigDecimal rateAmount = calculateConstantRateAmount(
                q, referenceAmount, referenceDuration, interestAmount, residualAmount);

        BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

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
        BigDecimal q = calculateQ(inputData.getInterestPercent());


        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);

        BigDecimal rateAmount = calculateConstantRateAmount(
                q, referenceAmount, referenceDuration, interestAmount, residualAmount);

        BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateQ(BigDecimal interestPercent) {
        return interestPercent.divide(YEAR, 50, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);
    }

    private BigDecimal calculateConstantRateAmount(BigDecimal q,
                                                   BigDecimal amount,
                                                   BigDecimal monthsDuration,
                                                   BigDecimal interestAmount,
                                                   BigDecimal residualAmount
    ) {
        BigDecimal rateAmount = amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue())
                        .subtract(BigDecimal.ONE), 50, RoundingMode.HALF_UP);

        return compareWithResidual(rateAmount, interestAmount, residualAmount);
    }


    private BigDecimal compareWithResidual(BigDecimal rateAmount, BigDecimal interestAmount, BigDecimal residualAmount) {
        BigDecimal amount = rateAmount.subtract(interestAmount);

        return amount.compareTo(residualAmount) >= 0 ? residualAmount.add(interestAmount) : rateAmount;
    }

    private BigDecimal calculateCapitalAmount(BigDecimal rateAmount,
                                              BigDecimal interestAmount,
                                              BigDecimal residualAmount) {
        BigDecimal capitalAmount = rateAmount.subtract(interestAmount);

        return capitalAmount.compareTo(residualAmount) >= 0 ? residualAmount : capitalAmount;
    }

}
