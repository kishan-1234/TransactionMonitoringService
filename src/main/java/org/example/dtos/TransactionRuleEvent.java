package org.example.dtos;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.models.Transaction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionRuleEvent {
    private String accountNbr;
    private int ruleId;
    private TriggeringTransaction triggeringTransaction;

    public static TransactionRuleEvent convertToTransactionRuleEvent(int ruleId, Transaction transaction) {
        return TransactionRuleEvent.builder()
                .accountNbr(transaction.getAccountNumber())
                .ruleId(ruleId)
                .triggeringTransaction(TriggeringTransaction.builder()
                        .tranId(transaction.getId())
                        .tranDirection(transaction.getDirection().name())
                        .tranTime(transaction.getTime())
                        .accNmbr(transaction.getAccountNumber())
                        .tranAmnt(transaction.getAmount())
                        .build())
                .build();
    }
}
