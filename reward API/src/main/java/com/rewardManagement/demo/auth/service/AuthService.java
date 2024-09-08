package com.rewardManagement.demo.auth.service;


import com.rewardManagement.demo.auth.entity.User;
import com.rewardManagement.demo.auth.entity.UserRole;
import com.rewardManagement.demo.auth.repository.UserRepository;
import com.rewardManagement.demo.auth.utils.AuthResponse;
import com.rewardManagement.demo.auth.utils.LoginRequest;
import com.rewardManagement.demo.auth.utils.RegisterRequest;
import com.rewardManagement.demo.exceptions.UserNameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private static final SecureRandom secureRandom = new SecureRandom();

    public AuthResponse register(RegisterRequest registerRequest, UserRole role)  {

        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserNameNotFoundException("Username already exists: " + registerRequest.getUsername());
        }

        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        User savedUser = userRepository.save(user);



        return AuthResponse.builder()
                .message("User Registered successfully..")
                .username(savedUser.getUsername())
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getEmail()));


        var accToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accToken)
                .refreshToken(refreshToken.getRefreshToken())
                .message("User LoggedIn successfully")
                .username(user.getUsername())
                .build();
    }








    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}










