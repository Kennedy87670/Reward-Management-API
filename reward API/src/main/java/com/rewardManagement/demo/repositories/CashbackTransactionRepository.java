package com.rewardManagement.demo.repositories;

import com.rewardManagement.demo.entity.CashbackTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashbackTransactionRepository extends JpaRepository<CashbackTransaction,Long> {
    List<CashbackTransaction> findByCustomerReward_CustomerId(Integer customerId);
}
