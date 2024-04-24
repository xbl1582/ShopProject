package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class InfoDto  extends BaseDto {

    private Integer  id;
    private  String uuid;
    private  String  infoname;
    private  String  type;
    private  String  updatetime;
    private  String  path;
    private  String infosize;
    private  Integer downloadtime;
    private  Integer priority;
}
