package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("empower")
@Slf4j
public class EmpowerPoJo {
    @TableId
     private  Integer id;
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
    private  String licmd5code;
    private  String type;


}
