package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Overpayment;

import java.math.BigDecimal;

public interface OverpaymentCalculationService {
    Overpayment calculate(BigDecimal rateNumber, InputData inputData);


}
