package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("drivers")
@Slf4j
public class DriverPoJo {
    @TableId
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
