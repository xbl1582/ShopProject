package com.example.kstupgrade.com.obj.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PathClassDto  extends  BaseDto{
    private Integer id;
    private String uuid;
    private  String path;
    private  String pathname;
    private  Integer type;
}
