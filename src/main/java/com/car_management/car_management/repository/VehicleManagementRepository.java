package com.car_management.car_management.repository;

import com.car_management.car_management.entity.VehicleEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VehicleManagementRepository {

    private static final Map<String, List<VehicleEntity>> userVehichleListMap = new HashMap<>();
    private static final Map<String, VehicleEntity> vehichleIdVehichleMap = new HashMap<>();


    public List<VehicleEntity> getListOfVehiclesForUser(String emailId) {
        return userVehichleListMap.get(emailId);
    }

    public VehicleEntity getVehicleById(String vehicleId) {
        return vehichleIdVehichleMap.get(vehicleId);
    }

    public VehicleEntity saveVehichleEntity(VehicleEntity vehicleEntity) {
        vehichleIdVehichleMap.put(vehicleEntity.getVehicleId(), vehicleEntity);
        return vehichleIdVehichleMap.get(vehicleEntity.getVehicleId());
    }

    public void linkUserWithVehichle(String emailId, VehicleEntity vehicleEntity) {
        var list = userVehichleListMap.getOrDefault(emailId, new ArrayList<>());
        list.add(vehicleEntity);
        userVehichleListMap.put(emailId, list);
    }
}
