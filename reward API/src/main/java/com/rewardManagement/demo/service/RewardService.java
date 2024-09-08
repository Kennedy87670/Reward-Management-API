package com.rewardManagement.demo.service;

import com.rewardManagement.demo.dto.CashbackTransactionDTO;
import com.rewardManagement.demo.dto.CustomerRewardDTO;

import java.util.List;

public interface RewardService {

    CustomerRewardDTO getRewardsBalance(Integer customerId);

    List<CashbackTransactionDTO> getCashbackHistory(Integer customerId);
}
