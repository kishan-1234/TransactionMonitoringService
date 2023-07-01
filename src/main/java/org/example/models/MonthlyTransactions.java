package org.example.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
public class MonthlyTransactions {

    private int month;
    private double monthlyInTransactionTotal;
    private List<DailyTransactions> dailyTransactions;
    private Transaction lastInTransaction;
}
