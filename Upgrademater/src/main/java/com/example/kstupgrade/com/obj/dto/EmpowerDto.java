package com.example.kstupgrade.com.obj.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class EmpowerDto extends  BaseDto{
    private  String uuid;
    private  String sn;
    private  String sncode;
    private  String   lic;
    private  String   snpath;
    private  String   licpath;
    private  String   uploadtime;
    private  Integer  sndownloadtime;
    private  Integer  licdownloadtime;
    private  String   snsize;
    private  String   licsize;
    private  String  licmd5code;
    private  String type;

}
