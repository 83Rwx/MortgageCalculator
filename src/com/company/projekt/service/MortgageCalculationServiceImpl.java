package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Rate;
import com.company.projekt.model.Summary;

import java.util.List;

public class MortgageCalculationServiceImpl implements MortgageCalculationService {

    //przy dependency inversion moge decydowac jaki serwis bede wykorzystywac w implementacji danej klasy.
    //w tym przypadku decyduje sie na wykorzystanie PrintingService do drukowania
    //musze dac final przy PrintingService poniewaz to wymusi mi stworzenie konstruktora bym na etapie tworzenia obiektu
    //bym mógł przypisać dane do tego obiektu
    private final PrintingService printingService;

    private final RateCalculationService rateCalculationService;

    private final SummaryService summaryService;

    public MortgageCalculationServiceImpl(
            PrintingService printingService,
            RateCalculationService rateCalculationService,
            SummaryService summaryService) {
        this.printingService = printingService;
        this.rateCalculationService = rateCalculationService;
        this.summaryService = summaryService;
    }

    @Override
    public void calculate(InputData inputData) {
        printingService.printInputDateInfo(inputData);

        List<Rate> rates = rateCalculationService.calculate(inputData);

        Summary summary = summaryService.calculate(rates);
        printingService.printSummary(summary);

        printingService.printRates(rates);

    }
}
