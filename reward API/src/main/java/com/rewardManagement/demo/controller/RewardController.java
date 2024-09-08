package com.rewardManagement.demo.controller;


import com.rewardManagement.demo.dto.CashbackTransactionDTO;
import com.rewardManagement.demo.dto.CustomerRewardDTO;
import com.rewardManagement.demo.service.RewardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
@AllArgsConstructor
public class RewardController {

    private RewardService rewardService;


    @GetMapping("/balance")
    public CustomerRewardDTO getRewardsBalance(@RequestParam Integer customerId){
        return (CustomerRewardDTO) rewardService.getRewardsBalance(customerId);
    }


    @GetMapping("/history")
    public List<CashbackTransactionDTO> getCashbackHistory(@RequestParam Integer customerId){
        return rewardService.getCashbackHistory(customerId);
    }
}
