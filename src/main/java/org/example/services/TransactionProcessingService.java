package org.example.services;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.Transaction;
import org.example.models.DailyTransactions;
import org.example.models.MonthlyTransactions;
import org.example.repositories.AccountTransactionsRepository;

import java.util.List;


@Slf4j
public class TransactionProcessingService {

    @Inject
    private AccountTransactionsRepository accountTransactionsRepository;

    @Inject
    private TransactionRulesEvaluationService transactionRulesEvaluationService;
    public void processTransaction(Transaction transaction) {
        org.example.models.Transaction transactionModel = org.example.models.Transaction.convertDtoToModel(transaction);
        MonthlyTransactions monthlyTransactions = accountTransactionsRepository.addAccountTransaction(transactionModel);
        transactionRulesEvaluationService.EvaluateRulesOnTransaction(monthlyTransactions, transactionModel);
    }
}
