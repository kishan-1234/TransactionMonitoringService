package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.Transaction;
import org.example.services.TransactionProcessingService;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class FileReader {

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private TransactionProcessingService transactionProcessingService;

    public void ReadFileLineByLine(String filename) {
        try  {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String finalLine = line;
                    processLine(finalLine);
                }
            }
        } catch (IOException e) {
            log.error("Error in reading filename ::: {}", filename, e);
        }
    }

    private void processLine(String line) {
        try {
            Transaction transaction = objectMapper.readValue(line, Transaction.class);
            transactionProcessingService.processTransaction(transaction);
        } catch (Exception e) {
            log.error("Error in processing line ::: {}", line, e);
        }
    }
}
