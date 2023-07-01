package org.example.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Transaction {
    public static enum Direction {
        IN,
        OUT
    }
    private String id;
    private String time;
    private double amount;
    private Direction direction;
    private String accountNumber;

    public static Transaction convertDtoToModel(org.example.dtos.Transaction transaction) {
        return Transaction.builder()
                .id(transaction.getTranId())
                .time(transaction.getTranTime())
                .amount(transaction.getTranAmnt())
                .direction(transaction.getTranDirection().equals("IN")? Direction.IN:Direction.OUT)
                .accountNumber(transaction.getAccNmbr())
                .build();
    }
}
