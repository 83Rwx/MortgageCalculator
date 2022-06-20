package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Rate;
import com.company.projekt.model.Summary;

import java.util.List;

public interface PrintingService {

    //Wszystkie pola w interface sa public static final
    //z racji tego ze sa to pola static to moge odniesc sie do nich w metodzie main()
    // PrintingService.INTEREST_SUM

    String INTEREST_SUM = "SUMA ODESETEK: ";
    String OVERPAYMENT = "NADPLATA: ";
    String OVERPAYMENT_PROVISION = "PROWIZJA ZA NADPLATY: ";
    String OVERPAYMENT_REDUCE_RATE = "NADPLATA, ZMNIEJSZENIE RATY";
    String OVERPAYMENT_REDUCE_PERIOD = "NADPLATA, SKROCENIE OKRESU";
    String OVERPAYMENT_FREQUENCY = "SCHEMAT DOKONYWANIA NADPLAT: ";
    String LOSTS_SUM = "SUMA STRAT: ";
    String RATE_NUMBER = "NR: ";
    String YEAR = "ROK: ";
    String MONTH = "MIESIAC: ";
    String MONTHS = " MIESIECY: ";
    String DATE = "DATA: ";
    String RATE = "RATA: ";
    String INTEREST = "ODSETKI: ";
    String CAPITAL = "KAPITAL: ";
    String LEFT_AMOUNT = "PKWOTA: ";
    String LEFT_MONTHS = "PMSC: ";
    String MORTGAGE_AMOUNT = "KWOTA KREDYTU: ";
    String MORTGAGE_PERIOD = "OKRES KREDYTOWANIA: ";

    String CURRENCY = " ZL ";
    String NEW_LINE = "\n";
    String PERCENT = "% ";

    void printInputDateInfo(final InputData inputData);

    void printRates(List<Rate> rates);

    void printSummary(Summary summary);
}
