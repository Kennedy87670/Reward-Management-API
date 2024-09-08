package com.rewardManagement.demo.dto;

import lombok.Data;

@Data
public class CustomerRewardDTO {
    private String customerId;
    private double totalCashback;
    private double currentBalance;
}
