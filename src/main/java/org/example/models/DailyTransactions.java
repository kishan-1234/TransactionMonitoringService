package org.example.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
public class DailyTransactions {

    private int day;
    private double dailyTransactionTotal;
    private List<Transaction> transactions;

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
