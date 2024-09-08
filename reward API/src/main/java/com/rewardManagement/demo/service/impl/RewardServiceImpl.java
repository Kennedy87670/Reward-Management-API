package com.rewardManagement.demo.service.impl;

import com.rewardManagement.demo.auth.entity.User;
import com.rewardManagement.demo.auth.repository.UserRepository;
import com.rewardManagement.demo.dto.CashbackTransactionDTO;
import com.rewardManagement.demo.dto.CustomerRewardDTO;
import com.rewardManagement.demo.entity.CashbackTransaction;
import com.rewardManagement.demo.entity.CustomerReward;
import com.rewardManagement.demo.exceptions.UserNameNotFoundException;
import com.rewardManagement.demo.repositories.CashbackTransactionRepository;
import com.rewardManagement.demo.repositories.CustomerRewardRepository;
import com.rewardManagement.demo.service.RewardService;
import com.rewardManagement.demo.utils.CreateRewardRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class RewardServiceImpl implements RewardService {

    private final UserRepository userRepository;
    private final CustomerRewardRepository customerRewardRepository;
    private final CashbackTransactionRepository cashbackTransactionRepository;

    private final ModelMapper modelMapper;

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

    @Override
    public CustomerRewardDTO createRewardForUser(CreateRewardRequest request) {
        User user = getAuthenticatedUser();

        if (user == null) {
            throw new UserNameNotFoundException("Authenticated user not found.");
        }

        CustomerReward reward = new CustomerReward();
        reward.setTotalCashback(request.getTotalCashback());
        reward.setCurrentBalance(request.getCurrentBalance());
        reward.setUser(user);

        CustomerReward savedReward = customerRewardRepository.save(reward);

        user.setCustomerReward(savedReward);
        userRepository.save(user);  // Update the user with the associated reward

        return modelMapper.map(savedReward, CustomerRewardDTO.class);
    }

    @Override
    public CashbackTransactionDTO addTransaction(Integer customerId, CashbackTransactionDTO transactionDTO) {
        CustomerReward customerReward = customerRewardRepository.findById(customerId)
                .orElseThrow(() -> new UserNameNotFoundException("User with this ID " + customerId + " does not exist"));

        CashbackTransaction transaction = new CashbackTransaction();
        transaction.setAmountEarned(transactionDTO.getAmountEarned());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setCustomerReward(customerReward);

        CashbackTransaction savedTransaction = cashbackTransactionRepository.save(transaction);

        return modelMapper.map(savedTransaction, CashbackTransactionDTO.class);
    }



    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }


}
