package com.car_management.car_management.controller;

import com.car_management.car_management.dto.UserLoginDto;
import com.car_management.car_management.dto.UserRegisterDto;
import com.car_management.car_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(authService.signUp(userRegisterDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authService.refreshToken(userLoginDto));
    }
}
