package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.MortgageReference;
import com.company.projekt.model.Rate;
import com.company.projekt.model.RateAmounts;

public interface ReferenceCalculationService {

    MortgageReference calculate(InputData inputData);

    MortgageReference calculate(InputData inputData, RateAmounts rateAmounts, Rate previousRate);
}
