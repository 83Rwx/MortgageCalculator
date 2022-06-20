package com.company.projekt.service;

import com.company.projekt.model.Rate;

import java.math.BigDecimal;

public interface Function {

    BigDecimal calculate(Rate rate);
}
