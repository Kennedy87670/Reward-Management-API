//package com.movieClient.MovieClientApi.controller;
//
//import com.movieClient.MovieClientApi.auth.entities.RefreshToken;
//import com.movieClient.MovieClientApi.auth.entities.User;
//import com.movieClient.MovieClientApi.auth.jwtService.JwtService;
//import com.movieClient.MovieClientApi.auth.jwtService.RefreshTokenService;
//import com.movieClient.MovieClientApi.auth.services.AuthService;
//import com.movieClient.MovieClientApi.auth.utils.AuthResponse;
//import com.movieClient.MovieClientApi.auth.utils.LoginRequest;
//import com.movieClient.MovieClientApi.auth.utils.RefreshTokenRequest;
//import com.movieClient.MovieClientApi.auth.utils.RegisterRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/auth/")
//@RequiredArgsConstructor
//public class AuthController {
//
//
//    private final AuthService authService;
//    private final RefreshTokenService refreshTokenService;
//    private final JwtService jwtService;
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
//        return ResponseEntity.ok(authService.register(registerRequest));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(authService.login(loginRequest));
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//
//        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
//        User user = refreshToken.getUser();
//
//        String accessToken = jwtService.generateToken(user);
//
//        return ResponseEntity.ok(AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken.getRefreshToken())
//                .build());
//    }
//}

package com.rewardManagement.demo.controller;



import com.rewardManagement.demo.auth.entity.RefreshToken;
import com.rewardManagement.demo.auth.entity.User;
import com.rewardManagement.demo.auth.entity.UserRole;
import com.rewardManagement.demo.auth.service.AuthService;
import com.rewardManagement.demo.auth.service.JwtService;
import com.rewardManagement.demo.auth.service.RefreshTokenService;
import com.rewardManagement.demo.auth.utils.AuthResponse;
import com.rewardManagement.demo.auth.utils.LoginRequest;
import com.rewardManagement.demo.auth.utils.RefreshTokenRequest;
import com.rewardManagement.demo.auth.utils.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @PostMapping("/register/user")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest, UserRole.USER));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest registerRequest)  {
        return ResponseEntity.ok(authService.register(registerRequest, UserRole.ADMIN));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(jwt));

        if (jwtService.isTokenValid(jwt, userDetails)) {

            refreshTokenService.deleteRefreshToken(username);
            return ResponseEntity.ok("Logout successful");
        }

        return ResponseEntity.status(401).body("Invalid token");
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }










}
