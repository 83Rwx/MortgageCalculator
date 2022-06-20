package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.TimePoint;

import java.math.BigDecimal;

public interface TimePointService {

    TimePoint calculate(BigDecimal rateNumber, InputData inputData);
}
