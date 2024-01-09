package com.parkking.parkingservice.service.impl;

import com.parkking.parkingservice.dto.auth.JwtResponse;
import com.parkking.parkingservice.dto.auth.LoginRequest;
import com.parkking.parkingservice.dto.auth.TokenRefreshRequest;
import com.parkking.parkingservice.dto.auth.TokenRefreshResponse;
import com.parkking.parkingservice.exception.OtpMisMatchException;
import com.parkking.parkingservice.exception.TokenRefreshException;
import com.parkking.parkingservice.model.auth.ERole;
import com.parkking.parkingservice.model.auth.RefreshToken;
import com.parkking.parkingservice.model.auth.Role;
import com.parkking.parkingservice.model.user.User;
import com.parkking.parkingservice.repository.RoleRepository;
import com.parkking.parkingservice.repository.UserRepository;
import com.parkking.parkingservice.service.AuthService;
import com.parkking.parkingservice.service.core.RefreshTokenService;
import com.parkking.parkingservice.service.core.UserDetailsImpl;
import com.parkking.parkingservice.utils.JwtUtils;
import com.parkking.parkingservice.utils.SmsStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    SmsStrategy smsStrategy;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByMobileNo(loginRequest.getMobileNo()).orElseThrow(() -> new UsernameNotFoundException("mobile not registered"));
        if(loginRequest.getOtp().equalsIgnoreCase(user.getOtp())) {
            throw new OtpMisMatchException("otp verification failed- wrong otp");
        }
        if(Instant.now().isAfter(user.getOtpTime().plusSeconds(600))) {
            throw new OtpMisMatchException("otp verification failed- expired otp");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMobileNo(), loginRequest.getOtp()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromMobileNo(user.getMobileNo());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @Override
    public String sendOtp(String mobileNo, String role) {
        String otp = randomFourDigitGenerator();
        log.info("otp: " + otp);
        smsStrategy.makeSmsCall(mobileNo, otp);
        User user = userRepository.findByMobileNo(mobileNo).orElse(new User(mobileNo));
        user.setOtp(encoder.encode(otp));
        user.setOtpTime(Instant.now());
        addRole(role, user);
        user = userRepository.save(user);
        return isNewUser(user) ? "New user registered" : "Existing user";
    }

    private void addRole(String role, User user) {
        Set<Role> roles = user.getRoles();
        switch (role) {
            case "admin" -> {
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            }
            case "mod" -> {
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
            }
            default -> {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        }
        user.setRoles(roles);
    }

    private boolean isNewUser(User user) {
        return user.getCreatedAt()==null || user.getCreatedAt().equals(user.getUpdatedAt());
    }

    private static String randomFourDigitGenerator() {
        Random random = new Random();
        String otp = String.format("%04d", random.nextInt(10000));
        return otp;
    }
}
