package com.rewardManagement.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRewardRequest {
    private double totalCashback;
    private double currentBalance;
}
