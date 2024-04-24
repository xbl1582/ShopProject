package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class DriverDto extends  BaseDto{
    private  Integer id;
    private  String uuid;
    private  String drivername;
    private  String driversize;
    private  String type;
    private  String updatetime;
    private  Integer downloadtime;
    private  String path;
    private  String md5code;
    private  Integer priority;
}
