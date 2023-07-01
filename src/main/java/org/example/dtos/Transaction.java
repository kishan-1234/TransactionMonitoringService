package org.example.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Transaction{
    private String tranId;
    private String tranDirection;
    private String tranTime;
    private String accNmbr;
    private double tranAmnt;
}
