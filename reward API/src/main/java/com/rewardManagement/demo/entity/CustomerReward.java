package com.rewardManagement.demo.entity;

import com.rewardManagement.demo.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private double totalCashback;
    private double currentBalance;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "customerReward", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CashbackTransaction> cashbackTransactions;
}
