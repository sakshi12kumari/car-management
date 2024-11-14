package com.car_management.car_management.repository;

import com.car_management.car_management.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthRepository {

    private static final Map<String, UserEntity> emailIdToUserEntity = new HashMap<>();

    public UserEntity saveUser(UserEntity userEntity) {
        emailIdToUserEntity.put(userEntity.getEmailId(), userEntity);
        return userEntity;
    }

    public boolean existByEmailId(String emailId) {
        return emailIdToUserEntity.containsKey(emailId);
    }

    public UserEntity findByEmailId(String emailId) {
        return emailIdToUserEntity.get(emailId);
    }
}
