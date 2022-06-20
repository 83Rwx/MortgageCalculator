package com.company.projekt.service;

import com.company.projekt.model.Rate;
import com.company.projekt.model.Summary;

import java.util.List;

public interface SummaryService {

    Summary calculate(List<Rate> rates);
}
