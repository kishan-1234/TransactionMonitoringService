package org.example.monitoring.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.TransactionRuleEvent;
import org.example.services.TransactionRulesEvaluationService;

@Slf4j
public class MonthlyRuleMonitor extends RuleMonitoringClient{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public MonthlyRuleMonitor(TransactionRulesEvaluationService transactionRulesEvaluationService) {
        this.transactionRulesEvaluationService = transactionRulesEvaluationService;
    }

    @Override
    public void updateOnRuleTrigger(TransactionRuleEvent transactionRuleEvent) {
        try {
            String jsonString = objectMapper.writeValueAsString(transactionRuleEvent);
            log.info(jsonString);
        } catch(JsonProcessingException e) {
            log.error("Error in serializing TransactionRuleEvent ::: {}", transactionRuleEvent, e);
        }
    }
}
