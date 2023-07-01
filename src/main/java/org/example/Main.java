package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.monitoring.clients.DailyRuleMonitor;
import org.example.monitoring.clients.MonthlyRuleMonitor;
import org.example.services.TransactionRulesEvaluationService;
import org.example.utils.FileReader;

public class Main {
    public static void main(String[] args) {

        printBanner();
        Injector injector = Guice.createInjector(new DIModule());
        initializeRuleMonitoringClients(injector);
        injector.getInstance(FileReader.class).ReadFileLineByLine("transactions.json");
    }

    private static void initializeRuleMonitoringClients(Injector injector) {

        TransactionRulesEvaluationService transactionRulesEvaluationService = injector.getInstance(TransactionRulesEvaluationService.class);
        new DailyRuleMonitor(transactionRulesEvaluationService);
        new MonthlyRuleMonitor(transactionRulesEvaluationService);
    }

    private static void printBanner() {
        System.out.println("*******************************************");
        System.out.println("*       TRANSACTION MONITORING SERVICE    *");
        System.out.println("*******************************************");
        System.out.println();
    }
}