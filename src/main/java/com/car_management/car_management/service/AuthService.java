package com.car_management.car_management.service;

import com.car_management.car_management.dto.UserLoginDto;
import com.car_management.car_management.dto.UserRegisterDto;
import com.car_management.car_management.entity.UserEntity;
import com.car_management.car_management.repository.AuthRepository;
import com.car_management.car_management.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    public String signUp(UserRegisterDto userRegisterDto) {
        if (!StringUtils.hasText(userRegisterDto.getEmailId())) {
            throw new RuntimeException("EmailId can't be empty: " + userRegisterDto.getEmailId());
        }

        if (!StringUtils.hasText(userRegisterDto.getName())) {
            throw new RuntimeException("Name can't be empty: " + userRegisterDto.getName());
        }

        if (!StringUtils.hasText(userRegisterDto.getPassword())) {
            throw new RuntimeException("Password can't be empty");
        }

        if (authRepository.existByEmailId(userRegisterDto.getEmailId())) {
            throw new RuntimeException("User already Exist with email: " + userRegisterDto.getEmailId());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmailId(userRegisterDto.getEmailId());
        userEntity.setName(userRegisterDto.getName());
        userEntity.setPhoneNumber(userRegisterDto.getPhoneNumber());

        authRepository.saveUser(userEntity);
        return getToken(userRegisterDto.getEmailId(), userRegisterDto.getPassword());
    }

    public String login(UserLoginDto userLoginDto) {
        UserEntity userEntity = authRepository.findByEmailId(userLoginDto.getEmailId());

        if (userEntity == null) {
            throw new RuntimeException("User not found, Please signUp");
        }

        if (!isValidPassword(userLoginDto.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return getToken(userLoginDto.getEmailId(), userLoginDto.getPassword());
    }

    private boolean isValidPassword(String userPassword, String savedPassword) {
        return userPassword.equals(savedPassword);
    }

    public String getToken(String email, String password) {
        return jwtUtil.generateToken(email, password);
    }

    public String validateTokenAndGetEmail(String token) {
        return jwtUtil.validateTokenAndGetEmail(token);
    }

    public String refreshToken(UserLoginDto userLoginDto) {
        return jwtUtil.generateToken(userLoginDto.getEmailId(), userLoginDto.getPassword());
    }
}
