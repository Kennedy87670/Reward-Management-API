package com.rewardManagement.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CashbackTransactionDTO {
    private Long transactionId;
    private LocalDateTime transactionDate;
    private double amountEarned;
    private String description;
}
