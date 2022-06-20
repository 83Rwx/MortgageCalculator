package com.company.projekt;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Overpayment;
import com.company.projekt.model.RateType;
import com.company.projekt.service.*;

import java.math.BigDecimal;

public class Main {


    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withAmount(new BigDecimal("500000"))
                .withMonthsDuration(new BigDecimal(160))
                .withRateType(RateType.DECREASING)
                .withOverpaymentReduceWay(Overpayment.REDUCE_PERIOD);

        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(
                        new ConstantAmountsCalculationServiceImpl(),
                        new DecreasingAmountsCalculationServiceImpl()
                ),
                new OverpaymentCalculationServiceImpl(),
                new ResidualCalculationServiceImpl(),
                new ReferenceCalculationServiceImpl()
        );
        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create()
        );
        mortgageCalculationService.calculate(inputData);



    }
}
