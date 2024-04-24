package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("infos")
@Slf4j
public class InfoPoJo {
     @TableId
     private Integer  id;
     private  String uuid;
     private  String  infoname;
     private  String  type;
     private  String  updatetime;
     private  String  path;
     private  Integer downloadtime;
     private  Integer priority;
}
