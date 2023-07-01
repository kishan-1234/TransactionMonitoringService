package org.example.repositories;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.example.models.DailyTransactions;
import org.example.models.MonthlyTransactions;
import org.example.models.Transaction;
import org.example.utils.DateTimeHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class AccountTransactionsRepository {

    private static final Map<String, MonthlyTransactions> accountTransactionsMap = new HashMap<>();
    @Inject
    private DateTimeHelper dateTimeHelper;

    public MonthlyTransactions addAccountTransaction(Transaction transaction) {

        final String accountNumber = transaction.getAccountNumber();
        if(accountTransactionsMap.containsKey(accountNumber)) {
            updateTransactionInMap(transaction);
        } else {
            addTransactionInMap(transaction);
        }
        return accountTransactionsMap.get(accountNumber);
    }

    private void updateAccountTransactionsMap(String accountNumber, MonthlyTransactions monthlyTransactions) {
        accountTransactionsMap.put(accountNumber, monthlyTransactions);
    }

    private void addTransactionInMap(Transaction transaction) {

        MonthlyTransactions monthlyTransactions = buiidMonthStartTrasnaction(transaction);
        updateMonthlyInTransactionTotal(monthlyTransactions, transaction);
        updateAccountTransactionsMap(transaction.getAccountNumber(), monthlyTransactions);
    }

    private MonthlyTransactions buiidMonthStartTrasnaction(Transaction transaction) {

        Date dateOfTransaction = dateTimeHelper.getDateFromEpoch(transaction.getTime());

        DailyTransactions dailyTransactions = DailyTransactions.builder()
                .day(dateTimeHelper.getDayOfMonth(dateOfTransaction))
                .dailyTransactionTotal(transaction.getAmount())
                .transactions(Stream.of(transaction)
                        .flatMap(item -> Collections.singletonList(item).stream())
                        .collect(Collectors.toList()))
                .build();

        return MonthlyTransactions.builder()
                .month(dateTimeHelper.getMonthOfYear(dateOfTransaction))
                .dailyTransactions(Stream.of(dailyTransactions)
                        .flatMap(item -> Collections.singletonList(item).stream())
                        .collect(Collectors.toList())).build();
    }

    private void updateMonthlyInTransactionTotal(MonthlyTransactions monthlyTransactions, Transaction transaction) {
        if(transaction.getDirection().equals(Transaction.Direction.IN)) {
            monthlyTransactions.setMonthlyInTransactionTotal(monthlyTransactions.getMonthlyInTransactionTotal()+transaction.getAmount());
            monthlyTransactions.setLastInTransaction(transaction);
        }
    }

    private void updateTransactionInMap(Transaction transaction) {

        MonthlyTransactions existingMonthlyTransactions = accountTransactionsMap.get(transaction.getAccountNumber());
        Date dateOfCurrentTransaction  = dateTimeHelper.getDateFromEpoch(transaction.getTime());
        int currentTransactionMonth = dateTimeHelper.getMonthOfYear(dateOfCurrentTransaction);
        if(existingMonthlyTransactions.getMonth()==currentTransactionMonth) {
            checkAndProcessTransactionAsPerDay(transaction, dateTimeHelper.getDayOfMonth(dateOfCurrentTransaction),
                    existingMonthlyTransactions);
        } else {
            updateAccountTransactionsMap(transaction.getAccountNumber(), buiidMonthStartTrasnaction(transaction));
        }
    }

    private void checkAndProcessTransactionAsPerDay(Transaction currentTransaction, int currTransactionDay,
                                                    MonthlyTransactions monthlyTransactions) {

        List<DailyTransactions> exisitingTransactions = monthlyTransactions.getDailyTransactions();
        DailyTransactions existingLastDayTransaction = exisitingTransactions.get(exisitingTransactions.size()-1);
        if(existingLastDayTransaction.getDay()==currTransactionDay) {
            existingLastDayTransaction.addTransaction(currentTransaction);
            existingLastDayTransaction.setDailyTransactionTotal(existingLastDayTransaction.getDailyTransactionTotal()+currentTransaction.getAmount());
            exisitingTransactions.set(exisitingTransactions.size()-1, existingLastDayTransaction);
        } else {
            exisitingTransactions.add(DailyTransactions.builder()
                    .day(currTransactionDay)
                    .dailyTransactionTotal(currentTransaction.getAmount())
                    .transactions(Stream.of(currentTransaction)
                            .flatMap(item -> Collections.singletonList(item).stream())
                            .collect(Collectors.toList()))
                    .build());
        }
        monthlyTransactions.setDailyTransactions(exisitingTransactions);
        updateMonthlyInTransactionTotal(monthlyTransactions, currentTransaction);
        updateAccountTransactionsMap(currentTransaction.getAccountNumber(), monthlyTransactions);
    }
}
