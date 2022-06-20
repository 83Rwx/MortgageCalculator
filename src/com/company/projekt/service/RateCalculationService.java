package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Rate;

import java.util.List;

public interface RateCalculationService {

    List<Rate> calculate(final InputData inputData);
}
