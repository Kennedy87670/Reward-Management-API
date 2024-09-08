package com.rewardManagement.demo.controller;


import com.rewardManagement.demo.auth.entity.User;
import com.rewardManagement.demo.dto.CashbackTransactionDTO;
import com.rewardManagement.demo.dto.CustomerRewardDTO;
import com.rewardManagement.demo.entity.CustomerReward;
import com.rewardManagement.demo.service.RewardService;
import com.rewardManagement.demo.utils.CreateRewardRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor

public class RewardController {

    private final RewardService rewardService;


    @GetMapping("/balance")
    public CustomerRewardDTO getRewardsBalance() {
        Integer customerId = getCustomerIdFromAuthenticatedUser();
        if (customerId == null) {
            throw new IllegalStateException("User does not have an associated reward account.");
        }
        return rewardService.getRewardsBalance(customerId);
    }

    @GetMapping("/history")
    public List<CashbackTransactionDTO> getCashbackHistory() {
        Integer customerId = getCustomerIdFromAuthenticatedUser();
        if (customerId == null) {
            throw new IllegalStateException("User does not have an associated reward account.");
        }
        return rewardService.getCashbackHistory(customerId);
    }


    @PostMapping("/save")
    public ResponseEntity<CustomerRewardDTO> createReward(@RequestBody CreateRewardRequest request) {
        CustomerRewardDTO rewardDTO = rewardService.createRewardForUser(request);
        return ResponseEntity.ok(rewardDTO);
    }

    @PostMapping("/addTransaction")
    public ResponseEntity<CashbackTransactionDTO> addTransaction(@RequestBody CashbackTransactionDTO transactionDTO) {
        Integer customerId = getCustomerIdFromAuthenticatedUser();
        if (customerId == null) {
            throw new IllegalStateException("User does not have an associated reward account.");
        }
        CashbackTransactionDTO savedTransaction = rewardService.addTransaction(customerId, transactionDTO);
        return ResponseEntity.ok(savedTransaction);
    }



    private Integer getCustomerIdFromAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof User) {
            User user = (User) userDetails;
            CustomerReward reward = user.getCustomerReward();
            if (reward != null) {
                return reward.getCustomerId();
            }
        }
        return null;
    }
}
