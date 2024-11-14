package com.car_management.car_management.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class VehicleEntity {
    private String vehicleId;
    private String name;
    private String model;
    private Double price;
    private List<String> base64EncodedImages;
    private String title;
    private String description;
    private Map<String, String> tags;
    private String company;
    private String dealer;
}
