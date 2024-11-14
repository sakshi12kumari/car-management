package com.car_management.car_management.service;

import com.car_management.car_management.dto.VehicleDetailsDto;
import com.car_management.car_management.entity.VehicleEntity;
import com.car_management.car_management.repository.VehicleManagementRepository;
import com.car_management.car_management.util.HttpUtil;
import com.car_management.car_management.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleManagementService {

    private final VehicleManagementRepository vehicleManagementRepository;
    private final AuthService authService;

    public List<VehicleDetailsDto> getListOfVehiclesForUser(String emailId) {

        List<VehicleEntity> vehicleEntities = vehicleManagementRepository.getListOfVehiclesForUser(emailId);

        if (CollectionUtils.isEmpty(vehicleEntities)) {
            return Collections.emptyList();
        }

        List<VehicleDetailsDto> responseList = new ArrayList<>();
        vehicleEntities.forEach(vehicleEntity -> {
                    VehicleDetailsDto vehicleDetailsDto = new VehicleDetailsDto();
                    vehicleDetailsDto.setVehicleId(vehicleEntity.getVehicleId());
                    vehicleDetailsDto.setName(vehicleEntity.getName());
                    vehicleDetailsDto.setModel(vehicleEntity.getModel());
                    vehicleDetailsDto.setPrice(vehicleEntity.getPrice());
                    vehicleDetailsDto.setBase64EncodedImages(vehicleEntity.getBase64EncodedImages());

                    responseList.add(vehicleDetailsDto);
                }
        );
        return responseList;
    }


    public VehicleDetailsDto getDetailsForVehicle(String vehicleId) {
        VehicleEntity vehicleEntity = vehicleManagementRepository.getVehicleById(vehicleId);

        return mapVehichleEntityToDto(vehicleEntity);
    }

    private VehicleDetailsDto mapVehichleEntityToDto(VehicleEntity vehicleEntity) {
        VehicleDetailsDto vehicleDetailsDto = new VehicleDetailsDto();
        vehicleDetailsDto.setVehicleId(vehicleEntity.getVehicleId());
        vehicleDetailsDto.setName(vehicleEntity.getName());
        vehicleDetailsDto.setModel(vehicleEntity.getModel());
        vehicleDetailsDto.setPrice(vehicleEntity.getPrice());
        vehicleDetailsDto.setBase64EncodedImages(vehicleEntity.getBase64EncodedImages());
        vehicleDetailsDto.setTitle(vehicleEntity.getTitle());
        vehicleDetailsDto.setDescription(vehicleEntity.getDescription());
        vehicleDetailsDto.setTags(vehicleEntity.getTags());
        vehicleDetailsDto.setCompany(vehicleEntity.getCompany());
        vehicleDetailsDto.setDealer(vehicleEntity.getDealer());
        return vehicleDetailsDto;
    }

    public VehicleDetailsDto updateVehichleDetails(VehicleDetailsDto vehicleDetailsDto) {

        VehicleEntity vehicleEntity = vehicleManagementRepository.getVehicleById(vehicleDetailsDto.getVehicleId());
        vehicleEntity.setName(vehicleDetailsDto.getName());
        vehicleEntity.setModel(vehicleDetailsDto.getModel());
        vehicleEntity.setPrice(vehicleDetailsDto.getPrice());
        vehicleEntity.setBase64EncodedImages(vehicleDetailsDto.getBase64EncodedImages());
        vehicleEntity.setTitle(vehicleDetailsDto.getTitle());
        vehicleEntity.setDealer(vehicleDetailsDto.getDealer());
        vehicleEntity.setDescription(vehicleDetailsDto.getDescription());
        vehicleEntity.setTags(vehicleDetailsDto.getTags());
        vehicleEntity.setCompany(vehicleDetailsDto.getCompany());
        vehicleEntity = vehicleManagementRepository.saveVehichleEntity(vehicleEntity);
        return mapVehichleEntityToDto(vehicleEntity);
    }

    public VehicleDetailsDto createVehicleDetailsEntry(String email, VehicleDetailsDto vehicleDetailsDto) {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleId(UUID.randomUUID().toString());
        vehicleEntity.setName(vehicleDetailsDto.getName());
        vehicleEntity.setModel(vehicleDetailsDto.getModel());
        vehicleEntity.setPrice(vehicleDetailsDto.getPrice());
        vehicleEntity.setBase64EncodedImages(vehicleDetailsDto.getBase64EncodedImages());
        vehicleEntity.setTitle(vehicleDetailsDto.getTitle());
        vehicleEntity.setDealer(vehicleDetailsDto.getDealer());
        vehicleEntity.setDescription(vehicleDetailsDto.getDescription());
        vehicleEntity.setTags(vehicleDetailsDto.getTags());
        vehicleEntity.setCompany(vehicleDetailsDto.getCompany());

        vehicleManagementRepository.linkUserWithVehichle(email, vehicleEntity);
        vehicleEntity = vehicleManagementRepository.saveVehichleEntity(vehicleEntity);
        return mapVehichleEntityToDto(vehicleEntity);
    }

}
