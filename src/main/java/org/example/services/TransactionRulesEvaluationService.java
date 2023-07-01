package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.dtos.TransactionRuleEvent;
import org.example.models.DailyTransactions;
import org.example.models.MonthlyTransactions;
import org.example.monitoring.clients.RuleMonitoringClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class TransactionRulesEvaluationService {

    private List<RuleMonitoringClient> ruleMonitoringClients = new ArrayList<>();

    public void addRuleMonitoringClient(RuleMonitoringClient ruleMonitoringClient) {
        ruleMonitoringClients.add(ruleMonitoringClient);
    }

    public void EvaluateRulesOnTransaction(MonthlyTransactions monthlyTransactions, org.example.models.Transaction transaction) {
        Thread dailyRuleEvaluationThread = new Thread(() -> evaluateDailyAmountGreaterRule(monthlyTransactions, transaction));
        Thread monthlyRuleEvaluationThread = new Thread(() -> evaluateMonthlyAmountGreaterRule(monthlyTransactions, transaction));
        dailyRuleEvaluationThread.start();
        monthlyRuleEvaluationThread.start();
    }

    private void evaluateDailyAmountGreaterRule(MonthlyTransactions monthlyTransactions, org.example.models.Transaction transaction) {
        DailyTransactions lastDailyTransaction = monthlyTransactions.getDailyTransactions().get(monthlyTransactions.getDailyTransactions().size()-1);
        if(lastDailyTransaction.getDailyTransactionTotal()>10000) {
            publishTransactionRuleEvent(1, lastDailyTransaction.getTransactions().size()>1 ?
                    lastDailyTransaction.getTransactions().get(lastDailyTransaction.getTransactions().size()-2):lastDailyTransaction.getTransactions().get(0));
        }
    }

    private void evaluateMonthlyAmountGreaterRule(MonthlyTransactions monthlyTransactions, org.example.models.Transaction transaction) {
        if(monthlyTransactions.getMonthlyInTransactionTotal()>10000) {
            publishTransactionRuleEvent(2, monthlyTransactions.getLastInTransaction());
        }
    }

    private void publishTransactionRuleEvent(int ruleId, org.example.models.Transaction transaction) {
        publishTransactionRuleEventToAllClients(TransactionRuleEvent.convertToTransactionRuleEvent(ruleId, transaction));
    }

    private void publishTransactionRuleEventToAllClients(TransactionRuleEvent transactionRuleEvent) {
        ruleMonitoringClients.parallelStream()
                .forEach(ruleMonitoringClient -> ruleMonitoringClient.updateOnRuleTrigger(transactionRuleEvent));
    }
}
