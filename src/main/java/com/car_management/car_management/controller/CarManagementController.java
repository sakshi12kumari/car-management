package com.car_management.car_management.controller;

import com.car_management.car_management.dto.VehicleDetailsDto;
import com.car_management.car_management.service.AuthService;
import com.car_management.car_management.service.VehicleManagementService;
import com.car_management.car_management.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/car-management")
@RequiredArgsConstructor
public class CarManagementController {

    private final VehicleManagementService vehicleManagementService;
    private final AuthService authService;

    @GetMapping(value = "/listing")
    public ResponseEntity<?> listCarDetails() {
        String emailId = "";
        try {
            emailId = authService.validateTokenAndGetEmail(HttpUtil.getHeader("jwt-payload"));
        } catch (RuntimeException exception) {
            if ("Token has expired".equals(exception.getMessage())) {
                return ResponseEntity.status(401).body("Token Expired");
            }
            throw new RuntimeException(exception);
        }
        return ResponseEntity.ok(vehicleManagementService.getListOfVehiclesForUser(emailId));
    }

    @GetMapping(value = "/car/details/{vehichle-id}")
    public ResponseEntity<?> getVehichleDetails(@PathVariable("vehichle-id") String vehichleId) {
        return ResponseEntity.ok(vehicleManagementService.getDetailsForVehicle(vehichleId));
    }

    @PutMapping(value = "/update/details/{vehichle-id}")
    public ResponseEntity<?> updateCarDetails(@RequestBody VehicleDetailsDto vehicleDetailsDto) {
        return ResponseEntity.ok(vehicleManagementService.updateVehichleDetails(vehicleDetailsDto));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCarDetailsEntry(@RequestBody VehicleDetailsDto vehicleDetailsDto) {
        String emailId = "";
        try {
            emailId = authService.validateTokenAndGetEmail(HttpUtil.getHeader("jwt-payload"));
        } catch (RuntimeException exception) {
            if ("Token has expired".equals(exception.getMessage())) {
                return ResponseEntity.status(401).body("Token Expired");
            }
            throw new RuntimeException(exception);
        }
        return ResponseEntity.status(200)
                .body(vehicleManagementService.createVehicleDetailsEntry(emailId, vehicleDetailsDto));
    }

}
