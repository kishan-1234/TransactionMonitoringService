package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.example.repositories.AccountTransactionsRepository;
import org.example.services.TransactionProcessingService;
import org.example.services.TransactionRulesEvaluationService;
import org.example.utils.DateTimeHelper;
import org.example.utils.FileReader;

public class DIModule extends AbstractModule {


    @Override
    protected void configure() {

        bind(FileReader.class).in(Singleton.class);
        bind(ObjectMapper.class).in(Singleton.class);
        bind(TransactionProcessingService.class).in(Singleton.class);
        bind(DateTimeHelper.class).in(Singleton.class);
        bind(AccountTransactionsRepository.class).in(Singleton.class);
        bind(TransactionRulesEvaluationService.class).in(Singleton.class);
    }
}
