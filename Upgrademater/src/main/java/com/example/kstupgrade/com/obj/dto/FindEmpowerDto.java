package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class FindEmpowerDto extends  BaseDto{
    private  String uuid;
    private  String sn;
    private  String   lic;
    private  String   snpath;
    private  String   licpath;
    private  String   uploadtime;
    private  Integer  downloadtime;
    private  String   snsize;
    private  String   licsize;
    private  String sncode;
}
