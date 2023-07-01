package org.example.monitoring.clients;

import org.example.dtos.TransactionRuleEvent;
import org.example.services.TransactionRulesEvaluationService;

public abstract class RuleMonitoringClient {

    protected TransactionRulesEvaluationService transactionRulesEvaluationService;
    public abstract void updateOnRuleTrigger(TransactionRuleEvent transactionRuleEvent);
}
