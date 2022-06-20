package com.company.projekt.service;

import com.company.projekt.model.InputData;
import com.company.projekt.model.Overpayment;
import com.company.projekt.model.Rate;
import com.company.projekt.model.Summary;
import com.company.projekt.service.exception.MortgageException;

import java.util.List;
import java.util.Optional;

public class PrintingServiceImpl implements PrintingService {


    @Override
    public void printInputDateInfo(InputData inputData) {
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(MORTGAGE_AMOUNT)
                .append(inputData.getAmount())
                .append(CURRENCY)
                .append(NEW_LINE);
        msg.append(MORTGAGE_PERIOD)
                .append(inputData.getMonthsDuration())
                .append(MONTHS)
                .append(NEW_LINE);
        msg.append(INTEREST)
                .append(inputData.getInterestDisplay())
                .append(PERCENT)
                .append(NEW_LINE);

        Optional.ofNullable(inputData.getOverpaymentSchema())
                .filter(schema -> schema.size() > 0)
                .ifPresent(schema -> logOverpayment(msg, inputData));

        printMessage(msg);
    }

    private void logOverpayment(StringBuilder msg, InputData inputData) {
        switch (inputData.getOverpaymentReduceWay()) {
            case Overpayment.REDUCE_PERIOD:
                msg.append(OVERPAYMENT_REDUCE_PERIOD)
                        .append(NEW_LINE);
                break;
            case Overpayment.REDUCE_RATE:
                msg.append(OVERPAYMENT_REDUCE_RATE)
                        .append(NEW_LINE);
                break;
            default:
                throw new MortgageException();
        }

        msg.append(OVERPAYMENT_FREQUENCY)
                .append(inputData.getOverpaymentSchema())
                .append(NEW_LINE);
    }

    @Override
    public void printRates(List<Rate> rates) {
        String format = "%4s %3s | " +
                "%4s %3s | " +
                "%4s %2s | " +
                "%4s %2s | " +
                "%4s %8s | " +
                "%7s %8s | " +
                "%7s %8s | " +
                "%7s %8s | " +
                "%7s %10s | " +
                "%7s %3s ";

        for (Rate rate : rates) {
            String msg = String.format(format,
                    RATE_NUMBER, rate.getRateNumber(),
                    DATE, rate.getTimePoint()
                            .getDate(),
                    YEAR, rate.getTimePoint()
                            .getYear(),
                    MONTH, rate.getTimePoint()
                            .getMonth(),
                    RATE, rate.getRateAmounts()
                            .getRateAmount(),
                    INTEREST, rate.getRateAmounts()
                            .getInterestAmount(),
                    CAPITAL, rate.getRateAmounts()
                            .getCapitalAmount(),
                    OVERPAYMENT, rate.getRateAmounts().getOverpayment().getAmount(),
                    LEFT_AMOUNT, rate.getMortgageResidual()
                            .getAmount(),
                    LEFT_MONTHS, rate.getMortgageResidual()
                            .getDuration()
            );
            printMessage(new StringBuilder(msg));

            //dodaje odstepy co 12 miesiecy dla lepszej czytelności
            if (rate.getRateNumber()
                    .intValue() % 12 == 0) {
                System.out.println();
            }
        }
    }

    @Override
    public void printSummary(Summary summary) {
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(INTEREST_SUM)
                .append(summary.getInterestSum())
                .append(CURRENCY)
                .append(NEW_LINE);
        msg.append(OVERPAYMENT_PROVISION)
                .append(summary.getOverpaymentProvisions())
                .append(CURRENCY)
                .append(NEW_LINE);
        msg.append(LOSTS_SUM)
                .append(summary.getTotalLosts())
                .append(CURRENCY)
                .append(NEW_LINE);
        printMessage(msg);
    }

    private void printMessage(StringBuilder sb) {
        System.out.println(sb);
    }
}
