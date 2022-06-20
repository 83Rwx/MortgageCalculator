package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.MortgageResidual;
import com.company.projekt.model.Rate;
import com.company.projekt.model.RateAmounts;

public interface ResidualCalculationService {
    MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData);

    MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate);
}
