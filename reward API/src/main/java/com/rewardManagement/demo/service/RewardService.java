package com.rewardManagement.demo.service;

import com.rewardManagement.demo.dto.CashbackTransactionDTO;
import com.rewardManagement.demo.dto.CustomerRewardDTO;
import com.rewardManagement.demo.utils.CreateRewardRequest;
import lombok.Data;

import java.util.List;


public interface RewardService {

    CustomerRewardDTO getRewardsBalance(Integer customerId);

    List<CashbackTransactionDTO> getCashbackHistory(Integer customerId);

    CustomerRewardDTO createRewardForUser( CreateRewardRequest request);

    CashbackTransactionDTO addTransaction(Integer customerId, CashbackTransactionDTO transactionDTO);
}
