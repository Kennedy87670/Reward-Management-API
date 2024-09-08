package com.rewardManagement.demo.service.impl;

import com.rewardManagement.demo.dto.CashbackTransactionDTO;
import com.rewardManagement.demo.dto.CustomerRewardDTO;
import com.rewardManagement.demo.entity.CashbackTransaction;
import com.rewardManagement.demo.entity.CustomerReward;
import com.rewardManagement.demo.exceptions.UserNameNotFoundException;
import com.rewardManagement.demo.repositories.CashbackTransactionRepository;
import com.rewardManagement.demo.repositories.CustomerRewardRepository;
import com.rewardManagement.demo.service.RewardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class RewardServiceImpl implements RewardService {

    private CustomerRewardRepository customerRewardRepository;
    private CashbackTransactionRepository cashbackTransactionRepository;

    private ModelMapper modelMapper;

    @Override
    public CustomerRewardDTO getRewardsBalance(Integer customerId) {
        CustomerReward customerReward = customerRewardRepository.findById(customerId)
                .orElseThrow(() -> new UserNameNotFoundException("User with this ID " + customerId + " does not exist"));

        return modelMapper.map(customerReward, CustomerRewardDTO.class);
    }

    @Override
    public List<CashbackTransactionDTO> getCashbackHistory(Integer customerId) {
        List<CashbackTransaction> transactions = cashbackTransactionRepository.findByCustomerReward_CustomerId(customerId);

        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, CashbackTransactionDTO.class))
                .collect(Collectors.toList());
    }
}
