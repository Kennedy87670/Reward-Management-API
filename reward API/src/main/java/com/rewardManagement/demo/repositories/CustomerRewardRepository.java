package com.rewardManagement.demo.repositories;

import com.rewardManagement.demo.entity.CustomerReward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRewardRepository extends JpaRepository<CustomerReward, Integer> {
}
