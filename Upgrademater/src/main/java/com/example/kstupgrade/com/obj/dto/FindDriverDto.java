package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class FindDriverDto extends  BaseDto{
    private  String uuid;
    private  String drivername;
    private  String driversize;
    private  String type;
    private  String updatetime;
    private  Integer downloadtime;
    private  String path;
}
