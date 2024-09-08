package com.rewardManagement.demo.auth.service;

import com.rewardManagement.demo.auth.entity.RefreshToken;
import com.rewardManagement.demo.auth.entity.User;
import com.rewardManagement.demo.auth.repository.RefreshTokenRepository;
import com.rewardManagement.demo.auth.repository.UserRepository;
import com.rewardManagement.demo.exceptions.UserNameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNameNotFoundException("User not found with email : " + username));

        RefreshToken refreshToken = user.getRefreshToken();

        if (refreshToken == null) {
            long refreshTokenValidity = 30 * 1000;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));

        if (refToken.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh Token expired");
        }

        return refToken;
    }

    public void revokeRefreshToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNameNotFoundException("User not found with email : " + username));

        RefreshToken refreshToken = user.getRefreshToken();
        if (refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
        }
    }

    public void deleteRefreshToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNameNotFoundException("User not found with email : " + username));

        RefreshToken refreshToken = user.getRefreshToken();
        if (refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
        }
    }
}
