package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class FindInfoDto extends  BaseDto{
    private  String uuid;
    private  String  infoname;
    private  String  type;
    private  String  updatetime;
    private  String  path;
    private  Integer downloadtime;
}
