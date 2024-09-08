package com.rewardManagement.demo.auth.repository;


import com.rewardManagement.demo.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);

    boolean existsByUsername(String username);
}
